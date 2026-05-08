package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.services.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPlayerController {

    private final PlayerService playerService;

    public AdminPlayerController(PlayerService playerService) {
        this.playerService = playerService;
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

        return "redirect:/admin/players";
    }

    @PostMapping("/players/reject/{id}")
    public String reject(@PathVariable Integer id) {
        playerService.rejectPlayer(id);

        return "redirect:/admin/players";
    }

    @PostMapping("/players/remove{id}")
    public String remove(@PathVariable Integer id) {
        playerService.removePlayer(id);

        return "redirect:/admin/players";
    }
}
