package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import be.thomasmore.padelplanning.services.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ConfirmPaymentController {
    private final PadelDayRepository padelDayRepository;
    private final PlayerService playerService;

    public ConfirmPaymentController(PadelDayRepository padelDayRepository, PlayerService playerService) {
        this.padelDayRepository = padelDayRepository;
        this.playerService = playerService;
    }

    @GetMapping("/payments/{id}")
    public String payments(Model model,
                           @PathVariable Integer id) {
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);

        if (optionalPadelDay.isEmpty()) {
            return "redirect:/";
        }

        PadelDay padelDay = optionalPadelDay.get();

        model.addAttribute("padelDay", padelDay);
        model.addAttribute("paidPlayers", playerService.getPaidPlayers(padelDay));
        model.addAttribute("unpaidPlayers", playerService.getUnpaidPlayers(padelDay));
        model.addAttribute("reservedPlayers", padelDay.getReservedPlayers());

        return "admin/payments";
    }
}
