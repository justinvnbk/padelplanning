package be.thomasmore.padelplanning.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
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
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
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

    // Inject Twilio credentials from properties
    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.whatsapp.from}")
    private String twilioFromNumber;

    public NotificationService(NotificationRepository notificationRepository, PlayerRepository playerRepository) {
        this.notificationRepository = notificationRepository;
        this.playerRepository = playerRepository;
    }
    // Initialize Twilio when the service starts
    @PostConstruct
    public void initTwilio() {
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    public void createNotification(String title, String message, Collection<Player> recipients, boolean sendEmail, boolean sendWhatsApp) {
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
        // Trigger WhatsApp logic if requested
        if (sendWhatsApp) {
            sendWhatsAppMessage(title, message, recipients);
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

    //Uses template format to comply with WhatsApp's 24-hour rule.
    private void sendWhatsAppMessage(String title, String message, Collection<Player> recipients) {
        //numbers should be in E.164 format: "+32412345678"

        recipients.parallelStream().forEach(player -> {
            try {
                String userPhoneNumber = player.getTelephone();
                if (userPhoneNumber == null || userPhoneNumber.isBlank()) {
                    return; // Skip if player has no phone number registered
                }

                // Constructing the body to match a registered Sandbox Template
                String formattedMessage = String.format(
                        "Your padel notification *%s*:\n\n%s",
                        title,
                        message
                );

                Message twilioMessage = Message.creator(
                        new PhoneNumber("whatsapp:" + userPhoneNumber),
                        new PhoneNumber(twilioFromNumber),
                        formattedMessage
                ).create();

                //sout for Sandbox tests
                System.out.println("WhatsApp sent to " + player.getName() + ". SID: " + twilioMessage.getSid());
            } catch (Exception e) {
                System.out.println("Failed to send WhatsApp to " + player.getName() + ": " + e.getMessage());
            }
        });
    }
}
