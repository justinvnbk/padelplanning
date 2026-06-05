package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class PadelDayController {

    private final PadelDayRepository padelDayRepository;

    public PadelDayController(PadelDayRepository padelDayRepository) {
        this.padelDayRepository = padelDayRepository;
    }

    @GetMapping("/padeldays")
    public String padeldays(Model model) {
        Iterable<PadelDay> padeldays = padelDayRepository.findAllOrdered();
        model.addAttribute("padelDays", padeldays);
        return "admin/padeldays";
    }

    @GetMapping("/padeldaydetails/{padeldayid}")
    public String padeldaydetails(Model model,
                                  @PathVariable Integer padeldayid) {
        Optional<PadelDay> padelDay = padelDayRepository.findById(padeldayid);
        if (padelDay.isPresent()) {
            model.addAttribute("padelDay", padelDay.get());
        }else {
            model.addAttribute("padelDay", null);
        }
        return "admin/padeldaydetails";
    }
}
