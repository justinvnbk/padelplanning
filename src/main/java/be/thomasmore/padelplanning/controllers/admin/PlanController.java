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
    public String postPlan(Model model, @RequestParam int id, RedirectAttributes ra) {
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);
        if (optionalPadelDay.isPresent()) {
            PadelDay padelDay = optionalPadelDay.get();
            if (!padelDay.getSignedUpPlayers().isEmpty()) {
                createPadelDayPlanService.newPadelDayPlan(padelDay);
                notificationService.createNotification("Uitschrijven niet meer mogelijk", "Uitschrijven voor de padeldag op " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM")) + " is niet meer toegelaten.",
                        padelDay.getSignedUpPlayers(), false, padelDay.getId());
            }
            if (padelDay.getSignedUpPlayers().isEmpty()) {
                ra.addFlashAttribute("noPlayerError", "Er zijn geen spelers om een planning aan te maken!");
            }
        }
        return "redirect:/user/signup/" + id;
    }

    //Edit the plan
    @PostMapping("/planEdit")
    public String postPlanEdit(@RequestParam List<Integer> playerIds,
                               @RequestParam Integer padelDayId,
                               @RequestParam List<Integer> teamIds,
                               RedirectAttributes ra) {

        // 1. Get the whole planning
        Map<Integer, List<Integer>> editedTeamPlayers = new HashMap<>(); // teamId -> list of playerIds
        Map<LocalTime, List<Integer>> timeSlotPlayers = new HashMap<>(); // time -> list of all playerIds in that timeslot

        for (int i = 0; i < teamIds.size(); i++) {
            Integer currentTeamId = teamIds.get(i);
            Integer p1 = playerIds.get(i * 2);
            Integer p2 = playerIds.get((i * 2) + 1);

            // Prevent duplicate partners within the same team
            if (!p1.equals(0) && p1.equals(p2)) {
                ra.addFlashAttribute("error", "Een speler kan niet zijn eigen partner zijn.");
                return "redirect:/user/signup/" + padelDayId;
            }

            List<Integer> cleanIds = new ArrayList<>();

            if (!p1.equals(0)) cleanIds.add(p1);
            if (!p2.equals(0)) cleanIds.add(p2);

            editedTeamPlayers.put(currentTeamId, cleanIds);

            // Track who is playing at what time
            Team existingTeam = teamRepository.findById(currentTeamId).orElseThrow();
            LocalTime time = existingTeam.getMatches().get(0).getTimeSlot();

            timeSlotPlayers.computeIfAbsent(time, k -> new ArrayList<>()).addAll(cleanIds);
        }

        // 2. Validate availability using the new teams
        for (Integer currentTeamId : teamIds) {
            List<Integer> cleanIds = editedTeamPlayers.get(currentTeamId);

            Team existingTeam = teamRepository.findById(currentTeamId).orElseThrow();
            LocalTime time = existingTeam.getMatches().get(0).getTimeSlot();

            for (Integer pId : cleanIds) {
                // Check 1: Are they duplicated on the current page for this timeslot?
                // Count how many times this player appears in this timeslot
                long occurrencesOnPage = timeSlotPlayers.get(time).stream()
                        .filter(id -> id.equals(pId))
                        .count();

                if (occurrencesOnPage > 1) { // More than 1 means they are assigned to multiple teams at the same time
                    Player busyPlayer = playerRepository.findById(pId).orElseThrow();
                    ra.addFlashAttribute("error", busyPlayer.getName() + " is meerdere keren geselecteerd voor het tijdslot van " + time + ".");
                    return "redirect:/user/signup/" + padelDayId;
                }
            }
        }

        // 3. Save the changes
        boolean anyChangesMade = false;
        for (Integer currentTeamId : teamIds) {
            List<Integer> cleanIds = editedTeamPlayers.get(currentTeamId);

            Team existingTeam = teamRepository.findById(currentTeamId).orElseThrow();
            List<Integer> currentIds = existingTeam.getPlayers().stream()
                    .map(Player::getId)
                    .toList();

            if (!currentIds.equals(cleanIds)) {
                List<Player> players = playerRepository.findAllByIds(cleanIds);
                existingTeam.setPlayers(players);
                teamRepository.save(existingTeam);
                anyChangesMade = true;
            }
        }

        if (anyChangesMade) {
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
                "Er is een nieuw speelmoment gestart door " + loggedInAdmin.getName() + " voor " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM")) + ". Schrijf u nu in!",
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
        }else {
            model.addAttribute("padelDay", null);
        }
        model.addAttribute("fields", fields);
        return "admin/editpadelday";
    }

    @PostMapping("/editpadelday")
    public String postEditPadelDay(Model model,
                                   PadelDay padelDay,
                                   Principal principal,
                                   RedirectAttributes ra) {
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

            padelDay.setMatches(padelDayOld.getMatches());

            if (!new HashSet<>(padelDayOld.getFields()).containsAll(padelDay.getFields()) && !new HashSet<>(padelDay.getFields()).containsAll(padelDayOld.getFields())) {
                createPadelDayPlanService.newPadelDayPlan(padelDay);

            }


            List<Player> recipients = new ArrayList<>(padelDay.getSignedUpPlayers());
            recipients.addAll(padelDay.getReservedPlayers());

            if (!padelDayOld.getDate().equals(padelDay.getDate())) {
                notificationService.createNotification("Aanpassing speelmoment",
                        "Een padeldag waarvoor u ingeschreven bent vindt nu plaats op "
                                + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM 'om' HH:mm"))
                                + " in plaats van "
                                + padelDayOld.getDate().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")),
                        recipients,
                        true,
                        padelDay.getId());
            }

            if (!new HashSet<>(padelDayOld.getFields()).equals(new HashSet<>(padelDay.getFields()))){
                notificationService.createNotification("Aanpassing speelmoment",
                        "De velden van een padeldag waarvoor u bent ingeschreven, worden gewijzigd.",
                        recipients,
                        true,
                        padelDay.getId());
            }


            if (!padelDayOld.getDate().equals(padelDay.getDate()) || !new HashSet<>(padelDayOld.getFields()).equals(new HashSet<>(padelDay.getFields()))) {
                ra.addFlashAttribute("saved" , "Planning succesvol opgeslaan");
            }
        }
        padelDay.setNumberOfMatches(3);



        padelDayRepository.save(padelDay);

        return "redirect:/user/signup/" + padelDay.getId();
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
