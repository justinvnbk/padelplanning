package be.thomasmore.padelplanning.controllers;

import be.thomasmore.padelplanning.model.*;
import be.thomasmore.padelplanning.repositories.*;
import be.thomasmore.padelplanning.services.CreatePadelDayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {
    private final FieldRepository fieldRepository;
    private final PlayerRepository playerRepository;
    private final CreatePadelDayService createPadelDayService;

    public HomeController(FieldRepository fieldRepository, PlayerRepository playerRepository, CreatePadelDayService createPadelDayService) {
        this.fieldRepository = fieldRepository;
        this.playerRepository = playerRepository;
        this.createPadelDayService = createPadelDayService;
    }

    @GetMapping({"/","/home"})
    public String home(Model model){
        int timeSlots = 3; //The amount of timeslots available per player
        List<Field> availableFields = fieldRepository.getAvailable();
        LocalDateTime dateAndStartTime = LocalDateTime.of(2026,4,25,14,0);

        //First we need a new padelDay with a list of signedup players.
        PadelDay padelDay = new PadelDay();
        padelDay.setDate(dateAndStartTime);
        padelDay.setSignedUpPlayers(playerRepository.getAll());

        //This will create update padelDay with all linked entities (matches and teams) and save them to the database
        createPadelDayService.newPadelDayPlanning(padelDay, timeSlots, availableFields);

        model.addAttribute("padelDay", padelDay);
        return "home";
    }
}
