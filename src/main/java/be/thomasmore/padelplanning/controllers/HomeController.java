package be.thomasmore.padelplanning.controllers;

import be.thomasmore.padelplanning.model.*;
import be.thomasmore.padelplanning.repositories.*;
import be.thomasmore.padelplanning.services.CreatePadelDayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final PadelDayRepository padelDayRepository;

    public HomeController(PadelDayRepository padelDayRepository) {
        this.padelDayRepository = padelDayRepository;
    }

    @GetMapping({"/","/home"})
    public String home(Model model){
        PadelDay padelDay = padelDayRepository.getLast();

        List<LocalTime> getUniqueTimeSlots = padelDay.getMatches().stream()
                    .map(Match::getTimeSlot)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

        model.addAttribute("padelDay", padelDay);
        model.addAttribute("UniqueTimeSlots", getUniqueTimeSlots);
        return "home";
    }
}
