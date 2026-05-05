package be.thomasmore.padelplanning.config;

import be.thomasmore.padelplanning.model.Notification;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final PlayerRepository playerRepository;

    public GlobalControllerAdvice(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @ModelAttribute("currentUrl")
     public String getCurrentUrl(HttpServletRequest request) {
         return request.getRequestURI();
     }

     @ModelAttribute("hasUnseenNotifications")
     public boolean unseenNotifications(Principal principal) {

        if (principal == null) {
            return false;
        }

        Player player = playerRepository.findByEmail(principal.getName());

        return player.hasUnseenNotifications();
     }
}
