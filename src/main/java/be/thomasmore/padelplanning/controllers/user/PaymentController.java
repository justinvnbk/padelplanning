package be.thomasmore.padelplanning.controllers.user;

import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.NotificationService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class PaymentController {

    private final PadelDayRepository padelDayRepository;
    private final PlayerRepository playerRepository;
    private final NotificationService notificationService;

    public PaymentController(PadelDayRepository padelDayRepository, PlayerRepository playerRepository, NotificationService notificationService) {
        this.padelDayRepository = padelDayRepository;
        this.playerRepository = playerRepository;
        this.notificationService = notificationService;
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
                        .setSuccessUrl("http://localhost:8080/user/payment-success?id=" + id)
                        .setCancelUrl("http://localhost:8080/user/payment-cancel")
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
}
