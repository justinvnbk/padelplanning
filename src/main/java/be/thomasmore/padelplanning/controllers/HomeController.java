package be.thomasmore.padelplanning.controllers;

import be.thomasmore.padelplanning.entities.*;
import be.thomasmore.padelplanning.repositories.*;
import be.thomasmore.padelplanning.services.CreatePadelDayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalTime;
import java.util.List;

@Controller
public class HomeController {
    private final FieldRepository fieldRepository;
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final PadelDayRepository padelDayRepository;
    private final TeamRepository teamRepository;
    private final CreatePadelDayService createPadelDayService;

    public HomeController(FieldRepository fieldRepository, PlayerRepository playerRepository, MatchRepository matchRepository, PadelDayRepository padelDayRepository, TeamRepository teamRepository, CreatePadelDayService createPadelDayService) {
        this.fieldRepository = fieldRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.padelDayRepository = padelDayRepository;
        this.teamRepository = teamRepository;
        this.createPadelDayService = createPadelDayService;
    }

    @GetMapping({"/","/home"})
    public String home(Model model){
        int matchCountPerTimeSlot = 3; //The amount of matches played at the same time in 1 timeslot
        List<Field> fields = fieldRepository.getAvailable();
        LocalTime startTime = LocalTime.of(14,0,0);
        int matchDurationInMinutes = 40;
        List<Player> signedUpPlayers = playerRepository.getAll(); //The players who signed up for the padel day exluding the reserve list

        PadelDay padelDay = createPadelDayService.newPadelDay(matchCountPerTimeSlot, fields, startTime, matchDurationInMinutes, signedUpPlayers);

        padelDayRepository.save(padelDay);
        model.addAttribute("padelDay", padelDay);
        return "home";
    }
}
