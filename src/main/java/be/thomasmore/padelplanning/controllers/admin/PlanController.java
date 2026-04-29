package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.Field;
import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.repositories.FieldRepository;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.CreatePadelDayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class PlanController {
    private final FieldRepository fieldRepository;
    private final PlayerRepository playerRepository;
    private final CreatePadelDayService createPadelDayService;
    private final PadelDayRepository padelDayRepository;

    public PlanController(FieldRepository fieldRepository, PlayerRepository playerRepository, CreatePadelDayService createPadelDayService, PadelDayRepository padelDayRepository) {
        this.fieldRepository = fieldRepository;
        this.playerRepository = playerRepository;
        this.createPadelDayService = createPadelDayService;
        this.padelDayRepository = padelDayRepository;
    }

    @GetMapping("/plan")
    public String plan(Model model){
        PadelDay padelDay = padelDayRepository.getLast();

        model.addAttribute("padelDay", padelDay);
        return "admin/plan";
    }

    @GetMapping("/nieuwplan")
    public String nieuwplan(Model model){
        Iterable<Field> fields = fieldRepository.findAll();
        model.addAttribute("fields", fields);
        model.addAttribute("padelDay", new PadelDay());
        return "admin/nieuwplan";
    }

    @PostMapping("/nieuwplan")
    public String postNieuwPlan(Model model, PadelDay padelDay){
        padelDay.setSignedUpPlayers(playerRepository.getAll());
        padelDayRepository.save(padelDay);
        return "redirect:/admin/plan";
    }
}
