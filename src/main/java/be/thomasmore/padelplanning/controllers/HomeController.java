package be.thomasmore.padelplanning.controllers;

import be.thomasmore.padelplanning.model.*;
import be.thomasmore.padelplanning.repositories.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    private final PadelDayRepository padelDayRepository;
    private final PlayerRepository playerRepository;

    public HomeController(PadelDayRepository padelDayRepository, PlayerRepository playerRepository) {
        this.padelDayRepository = padelDayRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping({"/","/home"})
    public String home(Model model,
                       Authentication authentication){
        PadelDay padelDay = padelDayRepository.getLast();

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            Player player = playerRepository.findByEmail(email);
            model.addAttribute("playerName", player.getName());
        }

        model.addAttribute("padelDay", padelDay);
        return "home";
    }
}
