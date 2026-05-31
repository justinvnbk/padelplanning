package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ConfirmPaymentController {
    private final PadelDayRepository padelDayRepository;
    private final PlayerRepository playerRepository;
    private final NotificationService notificationService;

    public ConfirmPaymentController(PadelDayRepository padelDayRepository, PlayerRepository playerRepository, NotificationService notificationService) {
        this.padelDayRepository = padelDayRepository;
        this.playerRepository = playerRepository;
        this.notificationService = notificationService;
    }

    @GetMapping("/payments/{id}")
    public String payments(Model model,
                           @PathVariable Integer id) {
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);

        if (optionalPadelDay.isPresent()) {
            PadelDay padelDay = optionalPadelDay.get();

            List<Player> paidPlayers = padelDay.getSignedUpPlayers().stream()
                    .filter(player -> player.getPayedPadelDays().contains(padelDay))
                    .toList();

            List<Player> unpaidPlayers = padelDay.getSignedUpPlayers().stream()
                    .filter(player -> !player.getPayedPadelDays().contains(padelDay))
                    .toList();

            List<Player> reservedPlayers = padelDay.getReservedPlayers();

            model.addAttribute("padelDay", padelDay);
            model.addAttribute("paidPlayers", paidPlayers);
            model.addAttribute("unpaidPlayers", unpaidPlayers);
            model.addAttribute("reservedPlayers", reservedPlayers);
        }

        return "admin/payments";
    }
}
