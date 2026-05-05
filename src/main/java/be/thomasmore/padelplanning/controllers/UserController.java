package be.thomasmore.padelplanning.controllers;

import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.services.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController {
    private final PlayerService playerService;

    public UserController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/login")
    public String login (Model model, Principal principal) {

        if (principal != null) return "redirect:/";

        return "user/login";
    }

    @GetMapping("/logout")
    public String logout (Model model, Principal principal) {

        if (principal == null) return "redirect:/";

        return "user/logout";
    }

    @GetMapping("/register")
    public String registerForm (Model model, Principal principal) {
        if (principal != null) return "redirect:/";

        model.addAttribute("player", new Player());

        return "user/register";
    }

    @PostMapping("/register")
    public String registerSubmit (@ModelAttribute Player player, Model model) {
        if (playerService.emailExists(player.getEmail())) {
            model.addAttribute("error", "Email already exists!");

            return "user/register";
        }
        playerService.registerPlayer(player);
        return "redirect:/login?registered";
    }
}
