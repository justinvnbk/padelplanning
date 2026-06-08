package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.model.PreferredPlayside;
import be.thomasmore.padelplanning.model.SelfEvaluation;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.NotificationService;
import be.thomasmore.padelplanning.services.PlayerService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class PlayerController {

    private final PlayerService playerService;
    private final NotificationService notificationService;
    private final PlayerRepository playerRepository;
    private final JdbcTemplate jdbcTemplate;

    public PlayerController(PlayerService playerService, NotificationService notificationService, PlayerRepository playerRepository, JdbcTemplate jdbcTemplate) {
        this.playerService = playerService;
        this.notificationService = notificationService;
        this.playerRepository = playerRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/players")
    public String players(Model model,
                          @RequestParam(required = false) String keyword) {
        if(keyword == null) keyword = "";
        model.addAttribute("pendingPlayers", playerRepository.getPendingPlayers(keyword));
        model.addAttribute("approvedPlayers", playerRepository.getApprovedPlayers(keyword));

        List<String> emails = jdbcTemplate.queryForList("SELECT username FROM users", String.class);
        model.addAttribute("users", emails);

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
        model.addAttribute("player", playerRepository.findById(id).orElseThrow());
        model.addAttribute("playsides", PreferredPlayside.values());
        model.addAttribute("selfEvaluations", SelfEvaluation.values());
        return "admin/player-detail";
    }

    @PostMapping("/player-detail/{id}")
    public String playerDetailSubmit(@PathVariable Integer id,
                                     @ModelAttribute Player player,
                                     @RequestParam(required = false) Integer pRanking,
                                     Principal principal,
                                     RedirectAttributes redirectAttributes) {

        Player existing = playerRepository.findById(id).orElseThrow();

        String oldEmail = existing.getEmail();
        String newEmail = player.getEmail();
        String loggedInAdminEmail = principal.getName();

        boolean editingOwnProfile = oldEmail.equals(loggedInAdminEmail);

        existing.setTelephone(player.getTelephone());
        existing.setSelfEvaluation(player.getSelfEvaluation());
        existing.setPreferredPlayside(player.getPreferredPlayside());
        existing.setBirthDate(player.getBirthDate());
        existing.setpRanking(pRanking);
        existing.setName(player.getName());

        if (editingOwnProfile) {
            existing.setEmail(oldEmail);
        } else {
            existing.setEmail(newEmail);
            playerService.updatePlayerEmail(oldEmail, newEmail, loggedInAdminEmail);
        }

        playerService.updatePlayerProfile(existing);

        redirectAttributes.addAttribute("successMessage", "Spelergegevens opgeslagen.");

        return "redirect:/admin/player-detail/" + id + "?saved";
    }
}
