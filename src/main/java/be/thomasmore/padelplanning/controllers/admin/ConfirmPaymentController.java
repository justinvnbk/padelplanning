package be.thomasmore.padelplanning.controllers.admin;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
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
            model.addAttribute("padelDay", padelDay);

            List<Player> playersNotSignedUp = playerRepository.getAll().stream()
                    .filter(p -> !padelDay.getSignedUpPlayers().contains(p) && !padelDay.getReservedPlayers().contains(p)).toList();
            model.addAttribute("playersNotSignedUp", playersNotSignedUp);
        }

        return "admin/payments";
    }

    @PostMapping("/confirmPayment")
    public String postConfirmPayment(Principal principal,
                                     @RequestParam Integer playerId,
                                     @RequestParam Integer padelDayId) {
        Player admin = playerRepository.findByEmail(principal.getName());
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(padelDayId);

        if (optionalPadelDay.isPresent() && optionalPlayer.isPresent()) {
            PadelDay padelDay = optionalPadelDay.get();
            Player player = optionalPlayer.get();

            if (player.getPayedPadelDays().contains(padelDay)) {
                List<PadelDay> confirmedPayedPadelDays = player.getConfirmedPayedPadelDays();
                confirmedPayedPadelDays.add(padelDay);

                player.setConfirmedPayedPadelDays(player.getConfirmedPayedPadelDays());

                playerRepository.save(player);

                notificationService.createNotification("Betaling bevestigd",
                        admin.getName() + " heeft uw betaling voor de padel dag op " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " bevestigd",
                        List.of(player),
                        true,
                        true);
            }
        }
        return "redirect:/admin/payments/" + padelDayId;
    }

    @PostMapping("/unconfirmPayment")
    public String postUnconfirmPayment(Principal principal,
                                       @RequestParam Integer playerId,
                                       @RequestParam Integer padelDayId) {
        Player admin = playerRepository.findByEmail(principal.getName());
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(padelDayId);

        if (optionalPadelDay.isPresent() && optionalPlayer.isPresent()) {
            PadelDay padelDay = optionalPadelDay.get();
            Player player = optionalPlayer.get();

            if (player.getPayedPadelDays().contains(padelDay)) {
                List<PadelDay> confirmedPayedPadelDays = player.getConfirmedPayedPadelDays();
                confirmedPayedPadelDays.remove(padelDay);

                player.setConfirmedPayedPadelDays(player.getConfirmedPayedPadelDays());

                playerRepository.save(player);

                notificationService.createNotification("Betaling niet langer bevestigd!",
                        admin.getName() + " heeft uw betaling voor de padel dag op " + padelDay.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " niet langer bevestigd",
                        List.of(player),
                        true,
                        true);
            }
        }
        return "redirect:/admin/payments/" + padelDayId;
    }
}
