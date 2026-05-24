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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class PaymentController {

    private final PadelDayRepository padelDayRepository;
    private final PlayerRepository playerRepository;
    private final NotificationService notificationService;

    public PaymentController(PadelDayRepository padelDayRepository, PlayerRepository playerRepository,  NotificationService notificationService) {
        this.padelDayRepository = padelDayRepository;
        this.playerRepository = playerRepository;
        this.notificationService = notificationService;
    }

    @PostMapping("/create-checkout-session")
    @ResponseBody
    public String createCheckoutSession(@RequestParam Integer id) throws StripeException {
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName("Padel Day")
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("eur")
                        .setUnitAmount(1000L)
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
                        .addLineItem(lineItem)
                        .build();

        Session session = Session.create(params);

        return session.getUrl();
    }

    @GetMapping("/payment-success")
    public String paymentSuccess(Principal principal,
                                 @RequestParam Integer id) {

        Player player = playerRepository.findByEmail(principal.getName());
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);

        if (optionalPadelDay.isPresent()) {
            PadelDay padelDay = optionalPadelDay.get();

            if (!player.getPayedPadelDays().contains(padelDay)) {
                player.getPayedPadelDays().add(padelDay);
                playerRepository.save(player);

                notificationService.createNotification("Speler heeft betaald",
                        player.getName() + " heeft betaald voor de padel dag op: " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        playerRepository.findAllAdmins(),
                        true);
            }
        }

        return "redirect:/user/signup/" + id;
    }
}
