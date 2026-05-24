package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.model.PreferredPlayside;
import be.thomasmore.padelplanning.model.SelfEvaluation;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.NotificationService;
import be.thomasmore.padelplanning.services.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class PlayerController {

    private final PlayerService playerService;
    private final NotificationService notificationService;
    private final PlayerRepository playerRepository;

    public PlayerController(PlayerService playerService, NotificationService notificationService, PlayerRepository playerRepository) {
        this.playerService = playerService;
        this.notificationService = notificationService;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/players")
    public String players(Model model) {
        model.addAttribute("pendingPlayers", playerService.getPendingPlayers());
        model.addAttribute("approvedPlayers", playerService.getApprovedPlayers());

        return "admin/players";
    }

    @PostMapping("/players/approve/{id}")
    public String approve(@PathVariable Integer id) {
        playerService.approvePlayer(id);

        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            notificationService.createNotification("Welkom",
                    "U registratie is geaccepteerd, als er een padeldag gepland is kan u ervoor inschrijven via de navigatie.",
                    List.of(player),
                    true,
                    true);
        }

        return "redirect:/admin/players";
    }

    @PostMapping("/players/reject/{id}")
    public String reject(@PathVariable Integer id) {
        playerService.rejectPlayer(id);

        return "redirect:/admin/players";
    }

    @PostMapping("/players/remove/{id}")
    public String remove(@PathVariable Integer id) {
        playerService.removePlayer(id);

        return "redirect:/admin/players";
    }

    @GetMapping("/player-detail/{id}")
    public String playerDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("player", playerService.getPlayerById(id));
        model.addAttribute("playsides", PreferredPlayside.values());
        model.addAttribute("selfEvaluations", SelfEvaluation.values());
        return "admin/player-detail";
    }

    @PostMapping("/player-detail/{id}")
    public String playerDetailSubmit(@PathVariable Integer id,
                                     @ModelAttribute Player player,
                                     @RequestParam(required = false) Integer pRanking) {
        Player existing = playerService.getPlayerById(id);
        String oldEmail = existing.getEmail();

        existing.setTelephone(player.getTelephone());
        existing.setSelfEvaluation(player.getSelfEvaluation());
        existing.setPreferredPlayside(player.getPreferredPlayside());
        existing.setEmail(player.getEmail());
        existing.setBirthDate(player.getBirthDate());
        existing.setpRanking(pRanking);

        playerService.updatePlayerProfile(existing);
        playerService.updatePlayerEmail(oldEmail, player.getEmail());

        return "redirect:/admin/player-detail/" + id + "?saved";
    }
}
