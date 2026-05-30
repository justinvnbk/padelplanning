package be.thomasmore.padelplanning.controllers;

import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.model.PreferredPlayside;
import be.thomasmore.padelplanning.model.SelfEvaluation;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.NotificationService;
import be.thomasmore.padelplanning.services.PlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collection;

@Controller
public class UserController {
    private final PlayerRepository playerRepository;
    private final PlayerService playerService;
    private final NotificationService notificationService;

    public UserController(PlayerRepository playerRepository, PlayerService playerService, NotificationService notificationService) {
        this.playerRepository = playerRepository;
        this.playerService = playerService;
        this.notificationService = notificationService;
    }

    @GetMapping("/login")
    public String login(Model model, Principal principal) {

        if (principal != null) return "redirect:/";

        return "user/login";
    }

    @GetMapping("/logout")
    public String logout(Model model, Principal principal) {

        if (principal == null) return "redirect:/";

        return "user/logout";
    }

    @GetMapping("/register")
    public String registerForm(Model model, Principal principal) {
        if (principal != null) return "redirect:/";

        model.addAttribute("player", new Player());
        model.addAttribute("playsides", PreferredPlayside.values());
        model.addAttribute("selfEvaluations", SelfEvaluation.values());

        return "user/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute Player player,
                                 @RequestParam(required = false) String noPranking,
                                 @RequestParam(required = false) Integer pRanking,
                                 Model model) {


        Collection<Player> admins = playerRepository.findAllAdmins();


        if (playerService.emailExists(player.getEmail())) {
            model.addAttribute("error", "Email already exists!");
            model.addAttribute("playsides", PreferredPlayside.values());
            model.addAttribute("selfEvaluations", SelfEvaluation.values());
            return "user/register";
        }
        player.setpRanking(noPranking != null ? null : pRanking);
        playerService.registerPlayer(player);
        notificationService.createNotification("Nieuwe registratie",
                player.getName() + " wil zich registreren op de website.",
                admins,
                true);
        return "redirect:/login?registered";
    }

    @GetMapping("user/profile")
    public String profile(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        Player player = playerRepository.findByEmail(principal.getName());

        model.addAttribute("player", player);
        model.addAttribute("playsides", PreferredPlayside.values());
        model.addAttribute("selfEvaluations", SelfEvaluation.values());
        return "user/profile";
    }

    @PostMapping("user/profile")
    public String profileSubmit(@ModelAttribute Player player,
                                @RequestParam(required = false) Integer pRanking,
                                Principal principal,
                                Authentication authentication) {
        Player existing = playerRepository.findByEmail(principal.getName());

        existing.setTelephone(player.getTelephone());
        existing.setSelfEvaluation(player.getSelfEvaluation());
        existing.setPreferredPlayside(player.getPreferredPlayside());

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            existing.setEmail(player.getEmail());
            existing.setBirthDate(player.getBirthDate());
            existing.setpRanking(pRanking);
        }

        playerService.updatePlayerProfile(existing);
        return "redirect:/user/profile?saved";
    }
}
