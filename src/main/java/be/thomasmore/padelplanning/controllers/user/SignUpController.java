package be.thomasmore.padelplanning.controllers.user;

import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.CreatePadelDayPlanService;
import be.thomasmore.padelplanning.services.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/user")
public class SignUpController {
    private final PadelDayRepository padelDayRepository;
    private final PlayerRepository playerRepository;
    private final NotificationService notificationService;
    private final CreatePadelDayPlanService createPadelDayPlanService;

    public SignUpController(PadelDayRepository padelDayRepository, PlayerRepository playerRepository, NotificationService notificationService, CreatePadelDayPlanService createPadelDayPlanService) {
        this.padelDayRepository = padelDayRepository;
        this.playerRepository = playerRepository;
        this.notificationService = notificationService;
        this.createPadelDayPlanService = createPadelDayPlanService;
    }

    @GetMapping("/padeldays")
    public String padeldays(Model model, Principal principal) {

        List<PadelDay> padelDays = padelDayRepository.findAllOrdered().stream()
                .toList();

        Player loggedPlayer = playerRepository.findByEmail(principal.getName());

        Map<Integer, Boolean> signedUpMap = padelDays.stream().collect(Collectors.toMap(
                PadelDay::getId,
                padelDay -> padelDay.getSignedUpPlayers().contains(loggedPlayer)
                        || padelDay.getReservedPlayers().contains(loggedPlayer)
        ));

        model.addAttribute("padelDays", padelDays);
        model.addAttribute("signedUpMap", signedUpMap);

        return "user/padeldays";
    }

    @GetMapping("/signup/{padelDayId}")
    public String reserve(Model model,
                          Principal principal,
                          @PathVariable Integer padelDayId) {
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(padelDayId);
        boolean hasPlan = false;
        if (optionalPadelDay.isPresent()) {
            PadelDay padelDay = optionalPadelDay.get();
            model.addAttribute("padelDay", padelDay);

            Player loggedPlayer = playerRepository.findByEmail(principal.getName());
            model.addAttribute("isSignedUp", padelDay.getSignedUpPlayers().contains(loggedPlayer) || padelDay.getReservedPlayers().contains(loggedPlayer));
            model.addAttribute("hasPayed", loggedPlayer.getPayedPadelDays().contains(padelDay));
            hasPlan = !optionalPadelDay.get().getMatches().isEmpty();
        }else{
            model.addAttribute("padelDay", null);
        }
        model.addAttribute("hasPlan", hasPlan);
        return "user/signup";
    }

    @PostMapping("/signup")
    public String postSignUp(Model model,
                             @RequestParam int id,
                             Principal principal,
                             RedirectAttributes redirectAttributes) { // Added RedirectAttributes
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);
        Player player = playerRepository.findByEmail(principal.getName());

        if (optionalPadelDay.isPresent()) {
            PadelDay padelDay = optionalPadelDay.get();
            if (!padelDay.getSignedUpPlayers().contains(player) && !padelDay.getReservedPlayers().contains(player)) {
                List<Player> reservePlayers = padelDay.getReservedPlayers();
                List<Player> signedUpPlayers = padelDay.getSignedUpPlayers();

                reservePlayers.add(player);
                boolean movedToActive = false;

                //When there is 4 reserve player, they are added to the signedUp list
                if (reservePlayers.size() == 4 && signedUpPlayers.size() < padelDay.getFields().size() * 4) {
                    signedUpPlayers.addAll(reservePlayers);
                    movedToActive = true;

                    notificationService.createNotification("Inschrijving padeldag: " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM")),
                            "Er zijn voldoende spelers voor uw inschrijving te verwerken voor de padeldag op " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM")),
                            reservePlayers,
                            true);
                    reservePlayers.clear();
                    if (padelDay.getMatches().size() > 0) {
                        createPadelDayPlanService.newPadelDayPlan(padelDay);
                    }
                }

                // Set alert messages based on registration status
                if (movedToActive) {
                    redirectAttributes.addFlashAttribute("alertSuccess", "Je bent succesvol ingeschreven! Er zijn genoeg spelers om een match te vullen.");
                } else {
                    redirectAttributes.addFlashAttribute("alertWarning", "Je bent toegevoegd aan de reservelijst. Wacht op 3 extra spelers.");
                }

                padelDay.setSignedUpPlayers(signedUpPlayers);
                padelDay.setReservedPlayers(reservePlayers);
                padelDayRepository.save(padelDay);
            }
        }
        return "redirect:/user/signup/" + id;
    }

    @PostMapping("/signout")
    public String postSignOut(Model model,
                              @RequestParam int id,
                              Principal principal,
                              RedirectAttributes redirectAttributes) { // Added RedirectAttributes
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);
        if (optionalPadelDay.isPresent()) {
            PadelDay padelDay = optionalPadelDay.get();
            List<Player> reservePlayers = padelDay.getReservedPlayers();
            List<Player> signedUpPlayers = padelDay.getSignedUpPlayers();
            List<Player> admins = playerRepository.findAllAdmins();

            Player player = playerRepository.findByEmail(principal.getName());

            if (reservePlayers.contains(player)) {
                reservePlayers.remove(player);
                redirectAttributes.addFlashAttribute("alertInfo", "U bent succesvol uitgeschreven van de reservelijst.");
            } else if (signedUpPlayers.contains(player)) {
                signedUpPlayers.remove(player);
                boolean cascadeTriggered = false;

                //When there is no reserve players, 3 last sign-ups are also signed out te keep the right amount of people to fill the matches
                if (reservePlayers.isEmpty()) {
                    cascadeTriggered = true;
                    Collection<Player> forcedToSignOut = new ArrayList<Player>();
                    int counter = signedUpPlayers.size() - 1;
                    while (signedUpPlayers.size() % 4 != 0) {
                        forcedToSignOut.add(signedUpPlayers.get(counter));

                        reservePlayers.add(signedUpPlayers.get(counter));
                        signedUpPlayers.remove(signedUpPlayers.get(counter));

                        counter--;
                    }
                    //Notifications
                    notificationService.createNotification("Uitschrijving padeldag: " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM")),
                            "Een van de laatste 4 inschrijvingen heeft zich uitgeschreven. U komt terug op de reservelijst.",
                            forcedToSignOut,
                            true);
                    notificationService.createNotification("Ontbrekende speler: " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM")),
                            "Een van de laatste 4 inschrijvingen heeft zich uitgeschreven. Drie spelers staan weer op de reservelijst.",
                            admins,
                            true);

                    if (LocalDateTime.now().plusHours(4).isAfter(padelDay.getDate())) {
                        notificationService.createNotification("SNEL IEMAND NODIG VOOR VANDAAG",
                                "Iemand heeft zich binnen de laatste 4 uur uitgeschreven, schrijf je nog snel in om ons te vervoledigen",
                                playerRepository.getAll(),
                                true);
                    }

                } else {
                    Collection<Player> newSignedUpPlayer = new ArrayList<Player>();

                    signedUpPlayers.add(reservePlayers.get(0));
                    newSignedUpPlayer.add(reservePlayers.get(0));
                    reservePlayers.remove(reservePlayers.get(0));

                    notificationService.createNotification("Inschrijving padeldag: " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM")),
                            "Er zijn voldoende spelers voor uw inschrijving te verwerken voor de padeldag te " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM")),
                            newSignedUpPlayer,
                            true);
                }

                if (cascadeTriggered) {
                    redirectAttributes.addFlashAttribute("alertDanger", "U bent succesvol uitgeschreven.");
                } else {
                    redirectAttributes.addFlashAttribute("alertSuccess", "U bent succesvol uitgeschreven. Een speler uit de reservelijst neemt uw plaats in!");
                }

                if (padelDay.getMatches().size() > 0) {
                    createPadelDayPlanService.newPadelDayPlan(padelDay);
                }
            }
            padelDay.setSignedUpPlayers(signedUpPlayers);
            padelDay.setReservedPlayers(reservePlayers);
            padelDayRepository.save(padelDay);
        }
        return "redirect:/user/signup/" + id;
    }
}
