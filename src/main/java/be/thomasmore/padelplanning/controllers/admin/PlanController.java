package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.Field;
import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.repositories.FieldRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.CreatePadelDayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class PlanController {
    private final FieldRepository fieldRepository;
    private final PlayerRepository playerRepository;
    private final CreatePadelDayService createPadelDayService;

    public PlanController(FieldRepository fieldRepository, PlayerRepository playerRepository, CreatePadelDayService createPadelDayService) {
        this.fieldRepository = fieldRepository;
        this.playerRepository = playerRepository;
        this.createPadelDayService = createPadelDayService;
    }

    @GetMapping("/plan")
    public String plan(Model model){
        int timeSlots = 3; //The amount of timeslots available per player
        List<Field> availableFields = fieldRepository.getAvailable();
        LocalDateTime dateAndStartTime = LocalDateTime.of(2026,4,25,14,0);
        int matchDurationInMinutes = 40; //doesn't include the pause in between matches

        //First we need a new padelDay with a list of signedup players.
        PadelDay padelDay = new PadelDay();
        padelDay.setDate(dateAndStartTime);
        padelDay.setSignedUpPlayers(playerRepository.getAll());

        //This will create update padelDay with all linked entities (matches and teams) and save them to the database
        createPadelDayService.newPadelDayPlanning(padelDay, timeSlots, availableFields, matchDurationInMinutes);

        model.addAttribute("padelDay", padelDay);
        return "admin/plan";
    }
}
