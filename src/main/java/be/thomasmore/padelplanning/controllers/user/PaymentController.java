package be.thomasmore.padelplanning.controllers.user;

import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class PaymentController {
    private final PlayerRepository playerRepository;
    private final PadelDayRepository padelDayRepository;
    private final NotificationService notificationService;

    public PaymentController(PlayerRepository playerRepository, PadelDayRepository padelDayRepository, NotificationService notificationService) {
        this.playerRepository = playerRepository;
        this.padelDayRepository = padelDayRepository;
        this.notificationService = notificationService;
    }

    @PostMapping("/payed")
    public String postPayed(Principal principal,
                            @RequestParam Integer id) {
        Player player = playerRepository.findByEmail(principal.getName());
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);

        if (optionalPadelDay.isPresent()) {
            PadelDay padelDay = optionalPadelDay.get();
            if (!player.getPayedPadelDays().contains(padelDay)) {
                List<PadelDay> payedPadelDays = player.getPayedPadelDays();
                payedPadelDays.add(padelDay);

                player.setPayedPadelDays(player.getPayedPadelDays());

                playerRepository.save(player);

                notificationService.createNotification("Speler heeft betaald",
                        player.getName() + " heeft betaald voor de padel dag op: " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        playerRepository.findAllAdmins());
            }
        }
        return "redirect:/user/signup/" + id;
    }
}
