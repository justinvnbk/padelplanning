package be.thomasmore.padelplanning.controllers;

import be.thomasmore.padelplanning.model.*;
import be.thomasmore.padelplanning.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;


@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(PadelDayController.class);
    private final PadelDayRepository padelDayRepository;
    private final PlayerRepository playerRepository;

    public HomeController(PadelDayRepository padelDayRepository, PlayerRepository playerRepository) {
        this.padelDayRepository = padelDayRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping({"/","/home"})
    public String home(Model model,
                       Authentication authentication,
                       Principal principal) {

        PadelDay padelDay = padelDayRepository.getLast();
        model.addAttribute("padelDay", padelDay);

        if (padelDay != null) {
            Player player = getPlayerDataForCurrentUser(principal);
            if (player != null) {
                PadelDay found = findPadelDayByIdInCollection(player.getPadelDay(), padelDay.getId());
                model.addAttribute("isGoing", found != null);
            } else {
                model.addAttribute("isGoing", false);
            }
        }

        if (authentication != null && authentication.isAuthenticated()) {
            Player player1 = playerRepository.findByEmail(authentication.getName());
            if (player1 != null) model.addAttribute("playerName", player1.getName());
        }

        return "home";
    }

    @PostMapping({"/padelgoing", "/padelgoing/{id}"})
    public String padelGoing (Principal principal, @PathVariable(required = false) Integer id) {
        if (id == null) return "redirect:/";
        if (principal == null) return "redirect:/";
        logger.info("padelGoing {}", id);

        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);
        Player player = getPlayerDataForCurrentUser(principal);

        if (optionalPadelDay.isPresent() && player != null) {
            PadelDay padelDayByIdInCollection = findPadelDayByIdInCollection(player.getPadelDay(), id);
            if (padelDayByIdInCollection == null) {
                player.getPadelDay().add(optionalPadelDay.get());
            } else {
                player.getPadelDay().remove(padelDayByIdInCollection);
            }
            playerRepository.save(player);
        }

        return "redirect:/";
    }

    private Player getPlayerDataForCurrentUser (Principal principal) {
        if (principal == null) return null;

        logger.info("getPlayer {}", principal.getName());
        Optional<Player> optionalPlayer = playerRepository.findByName(principal.getName());
        return optionalPlayer.orElse(null);
    }

    private PadelDay findPadelDayByIdInCollection (Collection<PadelDay> padelDays, int padelDayId) {
        if (padelDays == null) return null;

        for (PadelDay p : padelDays) {
            if (p.getId() == padelDayId) return p;
        }

        return null;
    }
}
