package be.thomasmore.padelplanning.services;

import io.mailtrap.client.MailtrapClient;
import io.mailtrap.config.MailtrapConfig;
import io.mailtrap.factory.MailtrapClientFactory;
import io.mailtrap.model.request.emails.Address;
import io.mailtrap.model.request.emails.MailtrapMail;

import java.util.List;
import be.thomasmore.padelplanning.model.Notification;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.NotificationRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final PlayerRepository playerRepository;
    //Token for mailing (current token is for sandbox environment)
    private static final String TOKEN = "5832c48314d3ccfec3b4115c6516b0a3";

    public NotificationService(NotificationRepository notificationRepository, PlayerRepository playerRepository) {
        this.notificationRepository = notificationRepository;
        this.playerRepository = playerRepository;
    }

    public void createNotification(String title, String message, Collection<Player> recipients, boolean sendEmail) {
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

        if (sendEmail) {
            sendEmail(title, message, recipients);
        }
    }

    private void sendEmail(String title, String message, Collection<Player> recipients) {
        final MailtrapConfig config = new MailtrapConfig.Builder()
                .sandbox(true)
                .inboxId(4645421L)
                .token(TOKEN)
                .build();

        final MailtrapClient client = MailtrapClientFactory.createMailtrapClient(config);

        List<Address> addresses = recipients.stream().map(r -> new Address(r.getEmail())).toList();

        final MailtrapMail mail = MailtrapMail.builder()
                .from(new Address("sandbox@example.com", "Mailtrap Test"))
                .to(addresses)
                .subject(title)
                .text(message)
                .build();

        //Currently just sout for logging, not important due to being in a sandbox environment
        try {
            System.out.println(client.send(mail));
        } catch (Exception e) {
            System.out.println("Caught exception : " + e);
        }
    }
}
