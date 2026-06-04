package be.thomasmore.padelplanning.controllers.user;

import be.thomasmore.padelplanning.model.Notification;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.NotificationRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.NotificationService; // Added import
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class NotificationsController {
    private final PlayerRepository playerRepository;
    private final NotificationService notificationService;

    public NotificationsController(PlayerRepository playerRepository, NotificationService notificationService) {
        this.playerRepository = playerRepository;
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public String notifications(Model model, Principal principal) {
        Player player = playerRepository.findByEmail(principal.getName());

        player.setHasUnseenNotifications(false);
        playerRepository.save(player);

        Set<Integer> expiredPadelDayIds = new HashSet<>();
        if (player.getNotifications() != null) {
            for (Notification n : player.getNotifications()) {
                if (n.getPadelDayId() != null && notificationService.isPadelDayExpired(n.getPadelDayId())) {
                    expiredPadelDayIds.add(n.getPadelDayId());
                }
            }
        }

        model.addAttribute("player", player);
        model.addAttribute("expiredPadelDayIds", expiredPadelDayIds);

        return "user/notifications";
    }
}
