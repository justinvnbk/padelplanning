package be.thomasmore.padelplanning.controllers.user;

import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class NotificationsController {
    private final PlayerRepository playerRepository;

    public NotificationsController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping("/notifications")
    public String notifications(Model model, Principal principal) {
        Player player = playerRepository.findByEmail(principal.getName());
        model.addAttribute("player", player);
        return "user/notifications";
    }
}
