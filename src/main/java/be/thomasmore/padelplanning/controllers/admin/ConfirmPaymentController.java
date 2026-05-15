package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ConfirmPaymentController {
    private final PadelDayRepository padelDayRepository;

    public ConfirmPaymentController(PadelDayRepository padelDayRepository) {
        this.padelDayRepository = padelDayRepository;
    }

    @GetMapping("/payments/{id}")
    public String payments(Model model,
                           @PathVariable Integer id) {
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);
        if (optionalPadelDay.isPresent()) {
            PadelDay padelDay = optionalPadelDay.get();
            model.addAttribute("padelDay", padelDay);
        }

        return "admin/payments";
    }
}
