package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.*;
import be.thomasmore.padelplanning.repositories.*;
import be.thomasmore.padelplanning.services.CreatePadelDayPlanService;
import be.thomasmore.padelplanning.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class PlanController {
    private final FieldRepository fieldRepository;
    private final PlayerRepository playerRepository;
    private final CreatePadelDayPlanService createPadelDayPlanService;
    private final MatchRepository matchRepository;
    private final PadelDayRepository padelDayRepository;
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    private final TeamRepository teamRepository;
    private final NotificationService notificationService;

    public PlanController(FieldRepository fieldRepository, PlayerRepository playerRepository, CreatePadelDayPlanService createPadelDayPlanService, MatchRepository matchRepository, PadelDayRepository padelDayRepository, TeamRepository teamRepository, NotificationService notificationService) {
        this.fieldRepository = fieldRepository;
        this.playerRepository = playerRepository;
        this.createPadelDayPlanService = createPadelDayPlanService;
        this.matchRepository = matchRepository;
        this.padelDayRepository = padelDayRepository;
        this.teamRepository = teamRepository;
        this.notificationService = notificationService;
    }

    @GetMapping("/plan")
    public String plan(Model model){
        Optional<PadelDay> optionalPadelDay = padelDayRepository.getLast(LocalDateTime.now());
        boolean hasPlan = false;
        if(optionalPadelDay.isPresent()){
            model.addAttribute("padelDay", optionalPadelDay.get());
            hasPlan = !optionalPadelDay.get().getMatches().isEmpty();
        }
        model.addAttribute("hasPlan", hasPlan);
        return "admin/plan";
    }

    //Create a new plan for the curren PadelDay
    @PostMapping("/plan")
    public String postPlan(Model model,@RequestParam int id){
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);
        if(optionalPadelDay.isPresent()){
            PadelDay padelDay = optionalPadelDay.get();
            if(!padelDay.getSignedUpPlayers().isEmpty()) {
                createPadelDayPlanService.newPadelDayPlanning(padelDay);
                notificationService.createNotification("Nieuwe planning", "Een nieuwe planning is beschikbaar voor " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM")), padelDay.getSignedUpPlayers());
            }
        }
        return "redirect:/admin/plan";
    }

    //Edit the plan
    @PostMapping("/planEdit")
    public String postPlanEdit(@RequestParam List<Integer> playerIds,
                               @RequestParam Integer teamId,
                               RedirectAttributes ra) {

        Team existingTeam = teamRepository.findById(teamId).orElseThrow();
        Match currentMatch = existingTeam.getMatches().get(0);
        LocalTime time = currentMatch.getTimeSlot();

        // om te voorkomen dat duplicate spelers in hetzelfde team zitten
        if (playerIds.size() >= 2 && playerIds.get(0).equals(playerIds.get(1))) {
            ra.addFlashAttribute("error", "Een speler kan niet zijn eigen partner zijn.");
            return "redirect:/admin/plan";
        }

        //Is de speler beschikbaar?
        for (Integer pId : playerIds) {

            // lus roept null eerst
            if (pId == 0) continue;

            // speelt de speler?
            if (matchRepository.isPlayerBusy(time, pId)) {

                //als de speler speelt op dit team, krijgen we true
                boolean alreadyInThisTeam = existingTeam.getPlayers().stream()
                        .anyMatch(p -> p.getId().equals(pId));

                //als false, dat betekent dat de speler al op een ander team speelt
                if (!alreadyInThisTeam) {
                    Player busyPlayer = playerRepository.findById(pId).orElseThrow();

                    ra.addFlashAttribute("error", busyPlayer.getName() + " speelt al op dit tijdslot.");

                    return "redirect:/admin/plan";
                }
            }
        }

        List<Integer> cleanIds = playerIds.stream().filter(id -> id != 0).toList();
        List<Player> players = playerRepository.findAllByIds(cleanIds);

        existingTeam.setPlayers(players);
        teamRepository.save(existingTeam);

        ra.addFlashAttribute("success", "Succesvol geüpdatet!");

        return "redirect:/admin/plan";
    }

    @GetMapping("/newpadelday")
    public String nieuwPadelday(Model model){
        Iterable<Field> fields = fieldRepository.findAll();
        model.addAttribute("fields", fields);
        model.addAttribute("padelDay", new PadelDay());
        return "admin/newpadelday";
    }

    @PostMapping("/newpadelday")
    public String postNieuwPadelDay(Model model,
                                    PadelDay padelDay,
                                    Principal principal) {
        Player loggedInAdmin = playerRepository.findByEmail(principal.getName());
        padelDayRepository.save(padelDay);
        notificationService.createNotification("Nieuw speelmoment opgestart",
                "Er is een nieuw speelmoment gestart door " + loggedInAdmin.getName() + " voor " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM") )+ ". Schrijf je nu in!",
                playerRepository.getAll());
        return "redirect:/admin/plan";
    }

    @GetMapping("/editpadelday/{id}")
    public String editPadelday(Model model,
                               @PathVariable Integer id){
        Iterable<Field> fields = fieldRepository.findAll();
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);

        if(optionalPadelDay.isPresent()){
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
        if(optionalPadelDayOld.isPresent()){
            PadelDay padelDayOld = optionalPadelDayOld.get();
            if(!padelDayOld.getMatches().isEmpty()){
                createPadelDayPlanService.newPadelDayPlanning(padelDay);
            }
        }
        padelDayRepository.save(padelDay);

        List<Player> recipients = padelDay.getSignedUpPlayers();
        recipients.addAll(padelDay.getReservedPlayers());

        notificationService.createNotification("Aanpassing speelmoment",
                "Het speelmoment is aangepast door " + loggedInAdmin.getName() + ". Bekijk het bij de inschrijvingen!",
                recipients);
        return "redirect:/admin/plan";
    }
}
