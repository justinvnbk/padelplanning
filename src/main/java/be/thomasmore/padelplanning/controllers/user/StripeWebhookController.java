package be.thomasmore.padelplanning.controllers.user;

import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.NotificationService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stripe.exception.StripeException;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/stripe")
public class StripeWebhookController {
    private final PlayerRepository playerRepository;
    private final PadelDayRepository padelDayRepository;
    private final NotificationService notificationService;

    @Value("${stripe.webhook.secret:true}")
    private String endpointSecret;

    public StripeWebhookController(PlayerRepository playerRepository,
                                   PadelDayRepository padelDayRepository,
                                   NotificationService notificationService) {
        this.playerRepository = playerRepository;
        this.padelDayRepository = padelDayRepository;
        this.notificationService = notificationService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body("Invalid signature");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            try {
                Session session = (Session) event.getDataObjectDeserializer()
                        .deserializeUnsafe();

                handleCheckoutSessionCompleted(session);
            } catch (StripeException e) {
                System.out.println("Could not deserialize session: " + e.getMessage());
                return ResponseEntity.badRequest().body("Could not deserialize session");
            }
        }

        return ResponseEntity.ok("Webhook received");
    }

    private void handleCheckoutSessionCompleted(Session session) {
        String playerIdString = session.getMetadata().get("playerId");
        String padelDayIdString = session.getMetadata().get("padelDayId");

        if (playerIdString == null || padelDayIdString == null) {
            return;
        }

        Integer playerId = Integer.valueOf(playerIdString);
        Integer padelDayId = Integer.valueOf(padelDayIdString);

        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(padelDayId);

        if (optionalPlayer.isEmpty() || optionalPadelDay.isEmpty()) {
            return;
        }

        Player player = optionalPlayer.get();
        PadelDay padelDay = optionalPadelDay.get();

        if (!player.getPayedPadelDays().contains(padelDay)) {
            player.getPayedPadelDays().add(padelDay);
            playerRepository.save(player);

            notificationService.createNotification(
                    "Speler heeft betaald",
                    player.getName() + " heeft betaald voor de padel dag op: " +
                            padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    playerRepository.findAllAdmins(),
                    true
            );
        }
    }
}
