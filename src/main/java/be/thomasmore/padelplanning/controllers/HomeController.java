package be.thomasmore.padelplanning.controllers;

import be.thomasmore.padelplanning.model.*;
import be.thomasmore.padelplanning.repositories.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final PadelDayRepository padelDayRepository;
    private final PlayerRepository playerRepository;

    public HomeController(PadelDayRepository padelDayRepository, PlayerRepository playerRepository) {
        this.padelDayRepository = padelDayRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        LocalDateTime now = LocalDateTime.now();
        Optional<PadelDay> optionalPadelDay = padelDayRepository.getNext(now.minusDays(1)); //So the plan can still be seen on the day itself

        if (optionalPadelDay.isPresent()) {
            List<LocalTime> getUniqueTimeSlots = optionalPadelDay.get().getMatches().stream()
                    .map(Match::getTimeSlot)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            model.addAttribute("UniqueTimeSlots", getUniqueTimeSlots);
            model.addAttribute("padelDay", optionalPadelDay.get());
        }
        return "home";
    }
}
