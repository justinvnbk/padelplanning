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

    private final PadelDayRepository padelDayRepository;

    public HomeController(PadelDayRepository padelDayRepository) {
        this.padelDayRepository = padelDayRepository;
    }

    @GetMapping({"/","/home"})
    public String home(Model model){
        PadelDay padelDay = padelDayRepository.getLast();

        model.addAttribute("padelDay", padelDay);
        return "home";
    }
}
