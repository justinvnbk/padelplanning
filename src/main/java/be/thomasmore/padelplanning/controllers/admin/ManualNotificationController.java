package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.NotificationService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")

public class ManualNotificationController {

    private final NotificationService notificationService;
    private final PlayerRepository playerRepository;

    public ManualNotificationController(NotificationService notificationService, PlayerRepository playerRepository) {
        this.notificationService = notificationService;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/createnotification")
    public String createNotification() {

        return "admin/createnotification";
    }

    @PostMapping("/createnotification")
    public String sendNotification(@RequestParam("title") String title,
                                   @RequestParam("message") String message,
                                   RedirectAttributes redirectAttributes) {

        List<Player> players = playerRepository.getAll();


        notificationService.createNotification(title, message, players, false);

        redirectAttributes.addFlashAttribute("successMessage", "Succesvol verzonden!");

        return "redirect:/admin/createnotification";
    }
}
