package be.thomasmore.padelplanning.services;

import be.thomasmore.padelplanning.model.Notification;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void createNotification(String title, String message, Collection<Player> recipients) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setRecipients(new ArrayList<>(recipients));
        notification.setSeen(false);
        notification.setDateTime(LocalDateTime.now());

        notificationRepository.save(notification);
    }
}
