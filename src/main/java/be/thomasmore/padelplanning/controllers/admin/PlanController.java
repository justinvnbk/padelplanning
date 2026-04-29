package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.Field;
import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.repositories.FieldRepository;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.CreatePadelDayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class PlanController {
    private final FieldRepository fieldRepository;
    private final PlayerRepository playerRepository;
    private final CreatePadelDayService createPadelDayService;
    private final PadelDayRepository padelDayRepository;
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    public PlanController(FieldRepository fieldRepository, PlayerRepository playerRepository, CreatePadelDayService createPadelDayService, PadelDayRepository padelDayRepository) {
        this.fieldRepository = fieldRepository;
        this.playerRepository = playerRepository;
        this.createPadelDayService = createPadelDayService;
        this.padelDayRepository = padelDayRepository;
    }

    @GetMapping("/plan")
    public String plan(Model model){
        Optional<PadelDay> optionalPadelDay = padelDayRepository.getLast();
        if(optionalPadelDay.isPresent()){
            model.addAttribute("padelDay", optionalPadelDay.get());
        }

        return "admin/plan";
    }
    @PostMapping("/plan")
    public String postPlan(Model model,@RequestParam int id){
        PadelDay padelDay = padelDayRepository.findById(id).get();
        createPadelDayService.newPadelDayPlanning(padelDay);
        return "redirect:/admin/plan";
    }

    @GetMapping("/newpadelday")
    public String nieuwPadelday(Model model){
        Iterable<Field> fields = fieldRepository.findAll();
        model.addAttribute("fields", fields);
        model.addAttribute("padelDay", new PadelDay());
        return "admin/newpadelday";
    }

    @PostMapping("/newpadelday")
    public String postNieuwPadelDay(Model model, PadelDay padelDay){
        padelDay.setSignedUpPlayers(playerRepository.getAll());
        padelDayRepository.save(padelDay);
        return "redirect:/admin/plan";
    }
}
