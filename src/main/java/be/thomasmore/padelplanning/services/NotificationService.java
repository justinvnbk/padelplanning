package be.thomasmore.padelplanning.services;

import be.thomasmore.padelplanning.model.Notification;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.NotificationRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final PlayerRepository playerRepository;

    public NotificationService(NotificationRepository notificationRepository, PlayerRepository playerRepository) {
        this.notificationRepository = notificationRepository;
        this.playerRepository = playerRepository;
    }

    public void createNotification(String title, String message, Collection<Player> recipients) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setRecipients(new ArrayList<>(recipients));
        notification.setDateTime(LocalDateTime.now());

        notificationRepository.save(notification);

        for (Player p : recipients) {
            p.setHasUnseenNotifications(true);
            playerRepository.save(p);
        }
    }
}
