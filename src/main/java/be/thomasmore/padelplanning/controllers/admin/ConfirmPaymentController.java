package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ConfirmPaymentController {
    private final PadelDayRepository padelDayRepository;
    private final PlayerRepository playerRepository;

    public ConfirmPaymentController(PadelDayRepository padelDayRepository, PlayerRepository playerRepository) {
        this.padelDayRepository = padelDayRepository;
        this.playerRepository = playerRepository;
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
}
