package be.thomasmore.padelplanning.controllers.user;

import be.thomasmore.padelplanning.model.ClubEvent;
import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.ClubEventRepository;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.NotificationService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class PaymentController {
    @Value("${app.base-url}")
    private String appBaseUrl;

    private final PadelDayRepository padelDayRepository;
    private final PlayerRepository playerRepository;
    private final ClubEventRepository clubEventRepository;

    public PaymentController(PadelDayRepository padelDayRepository, PlayerRepository playerRepository, ClubEventRepository clubEventRepository) {
        this.padelDayRepository = padelDayRepository;
        this.playerRepository = playerRepository;
        this.clubEventRepository = clubEventRepository;
    }

    @PostMapping("/create-checkout-session")
    @ResponseBody
    public String createCheckoutSession(@RequestParam Integer id, Principal principal) throws StripeException {

        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);
        Player player = playerRepository.findByEmail(principal.getName());

        if (optionalPadelDay.isEmpty() || !optionalPadelDay.get().isPublished()) {
            throw new IllegalStateException("Betaling niet toegestaan voor publicatie");
        }

        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName("Padel Day")
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("eur")
                        .setUnitAmount(800L)
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(appBaseUrl + "/user/payment-success?id=" + id)
                        .setCancelUrl(appBaseUrl + "/user/payment-cancel")
                        .putMetadata("playerId", player.getId().toString())
                        .putMetadata("padelDayId", id.toString())
                        .addLineItem(lineItem)
                        .build();

        Session session = Session.create(params);

        return session.getUrl();
    }

    @GetMapping("/payment-success")
    public String paymentSuccess(@RequestParam Integer id) {
        return "redirect:/user/signup/" + id;
    }

    @PostMapping("/events/{eventId}/pay")
    public String createEventCheckoutSession(@PathVariable Integer eventId,
                                             Principal principal) throws StripeException {

        Optional<ClubEvent> optionalClubEvent = clubEventRepository.findById(eventId);

        if (optionalClubEvent.isEmpty()) {
            return "redirect:/user/events";
        }

        ClubEvent clubEvent = optionalClubEvent.get();
        Player player = playerRepository.findByEmail(principal.getName());

        if (!clubEvent.isPublished()
                || !clubEvent.getParticipants().contains(player)
                || clubEvent.getPrice() == null
                || clubEvent.getPrice().signum() <= 0
                || player.getPaidClubEvents().contains(clubEvent)) {

            return "redirect:/user/events/" + eventId;
        }

        long priceInCents = clubEvent.getPrice()
                .movePointRight(2)
                .longValueExact();

        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(clubEvent.getTitle())
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("eur")
                        .setUnitAmount(priceInCents)
                        .setProductData(productData)
                        .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCustomerEmail(player.getEmail())
                .setSuccessUrl(appBaseUrl + "/user/event-payment-success?id=" + eventId)
                .setCancelUrl(appBaseUrl + "/user/events/" + eventId)
                .putMetadata("playerId", player.getId().toString())
                .putMetadata("clubEventId", eventId.toString())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(priceData)
                                .build()
                )
                .build();

        Session session = Session.create(params);

        return "redirect:" + session.getUrl();
    }

    @GetMapping("/event-payment-success")
    public String eventPaymentSuccess(@RequestParam Integer id) {
        return "redirect:/user/events/" + id;
    }
}
