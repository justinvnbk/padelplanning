package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.*;
import be.thomasmore.padelplanning.repositories.*;
import be.thomasmore.padelplanning.services.CreatePadelDayPlanService;
import be.thomasmore.padelplanning.services.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class PlanController {
    private final FieldRepository fieldRepository;
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final CreatePadelDayPlanService createPadelDayPlanService;
    private final PadelDayRepository padelDayRepository;
    private final TeamRepository teamRepository;
    private final NotificationService notificationService;

    public PlanController(FieldRepository fieldRepository, PlayerRepository playerRepository, MatchRepository matchRepository, CreatePadelDayPlanService createPadelDayPlanService, PadelDayRepository padelDayRepository, TeamRepository teamRepository, NotificationService notificationService) {
        this.fieldRepository = fieldRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.createPadelDayPlanService = createPadelDayPlanService;
        this.padelDayRepository = padelDayRepository;
        this.teamRepository = teamRepository;
        this.notificationService = notificationService;
    }


    //Create a new plan for the current PadelDay
    @PostMapping("/plan")
    public String postPlan(Model model, @RequestParam int id) {
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);
        if (optionalPadelDay.isPresent()) {
            PadelDay padelDay = optionalPadelDay.get();
            if (!padelDay.getSignedUpPlayers().isEmpty()) {
                createPadelDayPlanService.newPadelDayPlan(padelDay);
            }
        }
        return "redirect:/user/signup/" + id;
    }

    //Edit the plan
    @PostMapping("/planEdit")
    public String postPlanEdit(@RequestParam List<Integer> playerIds,
                               @RequestParam Integer padelDayId,
                               @RequestParam Integer teamId,
                               RedirectAttributes ra) {

        Team existingTeam = teamRepository.findById(teamId).orElseThrow();
        Match currentMatch = existingTeam.getMatches().get(0);
        LocalTime time = currentMatch.getTimeSlot();

        // to prevent a player from filling both positions in a team
        if (playerIds.size() >= 2 && playerIds.get(0).equals(playerIds.get(1))) {
            ra.addFlashAttribute("error", "Een speler kan niet zijn eigen partner zijn.");
            return "redirect:/user/signup/" + padelDayId;
        }

        // is the player available?
        for (Integer pId : playerIds) {

            // the loop calls zero first, and skips it
            if (pId == 0) continue;

            // check whether the player already play in the timeslot?
            if (matchRepository.isPlayerBusy(time, pId)) {

                // determine whether the player already on this team is, returns true if they are
                boolean alreadyInThisTeam = existingTeam.getPlayers().stream()
                        .anyMatch(p -> p.getId().equals(pId));

                // if false: the player is already on another team
                // send error message to front-end
                if (!alreadyInThisTeam) {
                    Player busyPlayer = playerRepository.findById(pId).orElseThrow();

                    ra.addFlashAttribute("error", busyPlayer.getName() + " speelt al op dit tijdslot.");

                    return "redirect:/user/signup/" + padelDayId;
                }
            }
        }

        // list of current players
        List<Integer> currentIds = existingTeam.getPlayers().stream()
                .map(Player::getId)
                .toList();

        // list of new players (if changed)
        List<Integer> cleanIds = playerIds.stream().filter(id -> id != 0).toList();

        // if the players are changed
        if (!currentIds.equals(cleanIds)) {
            List<Player> players = playerRepository.findAllByIds(cleanIds);
            existingTeam.setPlayers(players);
            teamRepository.save(existingTeam);

            ra.addFlashAttribute("success", "Succesvol geüpdatet!");

        } else {

            ra.addFlashAttribute("info", "Geen veranderingen gemaakt");
        }

        return "redirect:/user/signup/" + padelDayId;
    }

    @GetMapping("/newpadelday")
    public String nieuwPadelday(Model model) {

        PadelDay padelDay = new PadelDay();

        LocalDateTime nextSunday = LocalDateTime.now()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                .with(LocalTime.of(14, 0));


        padelDay.setDate(nextSunday);

        padelDay.setNumberOfMatches(3);

        Iterable<Field> fields = fieldRepository.findAll();
        model.addAttribute("fields", fields);
        model.addAttribute("padelDay", padelDay);
        return "admin/newpadelday";
    }

    @PostMapping("/newpadelday")
    public String postNieuwPadelDay(Model model,
                                    PadelDay padelDay,
                                    Principal principal) {
        Player loggedInAdmin = playerRepository.findByEmail(principal.getName());
        padelDay.setNumberOfMatches(3);
        padelDayRepository.save(padelDay);
        notificationService.createNotification("Nieuw speelmoment opgestart",
                "Er is een nieuw speelmoment gestart door " + loggedInAdmin.getName() + " voor " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM")) + ". Schrijf je nu in!",
                playerRepository.getAll(),
                true,
                padelDay.getId());
        return "redirect:/admin/padeldays";
    }

    @GetMapping("/editpadelday/{id}")
    public String editPadelday(Model model,
                               @PathVariable Integer id) {
        Iterable<Field> fields = fieldRepository.findAll();
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);

        if (optionalPadelDay.isPresent()) {
            PadelDay padelDay = optionalPadelDay.get();
            model.addAttribute("padelDay", padelDay);
        }
        model.addAttribute("fields", fields);
        return "admin/editpadelday";
    }

    @PostMapping("/editpadelday")
    public String postEditPadelDay(Model model,
                                   PadelDay padelDay,
                                   Principal principal) {
        Player loggedInAdmin = playerRepository.findByEmail(principal.getName());
        Optional<PadelDay> optionalPadelDayOld = padelDayRepository.findById(padelDay.getId());
        if (optionalPadelDayOld.isPresent()) {
            PadelDay padelDayOld = optionalPadelDayOld.get();
            List<Player> signedUpPlayers = padelDay.getSignedUpPlayers();
            List<Player> reservePlayers = padelDay.getReservedPlayers();

            while(signedUpPlayers.size() > padelDay.getFields().size()*4){
                for (int i = 0; i < 4; i++) {
                    reservePlayers.add(0,signedUpPlayers.get(signedUpPlayers.size() - 1));
                    signedUpPlayers.remove(signedUpPlayers.size() - 1);
                }
            }
            while((padelDay.getFields().size()*4) - signedUpPlayers.size() >= 4 && reservePlayers.size() >= 4){
                for (int i = 0; i < 4; i++) {
                    signedUpPlayers.add(reservePlayers.get(0));
                    reservePlayers.remove(0);
                }
            }

            if (padelDayOld.isPublished()) {
                createPadelDayPlanService.newPadelDayPlan(padelDay);
            }
        }
        padelDay.setNumberOfMatches(3);
        padelDayRepository.save(padelDay);


        List<Player> recipients = padelDay.getSignedUpPlayers();
        recipients.addAll(padelDay.getReservedPlayers());
        notificationService.createNotification("Aanpassing speelmoment",
                "Het speelmoment is aangepast door " + loggedInAdmin.getName() + ". Bekijk het bij de inschrijvingen!",
                recipients,
                true,
                padelDay.getId());
        return "redirect:/admin/padeldays";
    }

    @PostMapping("/publishplan/{id}")
    public String publishPlan(@PathVariable Integer id, RedirectAttributes ra) {
        Optional<PadelDay> optional = padelDayRepository.findById(id);
        if (optional.isPresent()) {
            PadelDay padelDay = optional.get();
            padelDay.setPublished(true);
            padelDayRepository.save(padelDay);
            notificationService.createNotification(
                    "Planning gepubliceerd",
                    "De planning voor " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM")) + " is gepubliceerd!",
                    padelDay.getSignedUpPlayers(),
                    true,
                    padelDay.getId()
            );
            ra.addFlashAttribute("success", "Planning gepubliceerd!");
        }
        return "redirect:/user/signup/" + id;
    }
}
