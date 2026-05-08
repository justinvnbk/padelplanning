package be.thomasmore.padelplanning.controllers;

import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.model.PreferredPlayside;
import be.thomasmore.padelplanning.model.SelfEvaluation;
import be.thomasmore.padelplanning.services.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("playsides", PreferredPlayside.values());
        model.addAttribute("selfEvaluations", SelfEvaluation.values());

        return "user/register";
    }

    @PostMapping("/register")
    public String registerSubmit (@ModelAttribute Player player,
                                  @RequestParam(required = false) String noPranking,
                                  @RequestParam(required = false) Integer pRanking,
                                  Model model) {
        if (playerService.emailExists(player.getEmail())) {
            model.addAttribute("error", "Email already exists!");
            model.addAttribute("playsides", PreferredPlayside.values());
            model.addAttribute("selfEvaluations", SelfEvaluation.values());
            return "user/register";
        }
        player.setpRanking(noPranking != null ? null : pRanking);
        playerService.registerPlayer(player);
        return "redirect:/login?registered";
    }
}
