package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.Field;
import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.model.Team;
import be.thomasmore.padelplanning.repositories.FieldRepository;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.repositories.TeamRepository;
import be.thomasmore.padelplanning.services.CreatePadelDayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private final TeamRepository teamRepository;

    public PlanController(FieldRepository fieldRepository, PlayerRepository playerRepository, CreatePadelDayService createPadelDayService, PadelDayRepository padelDayRepository, TeamRepository teamRepository) {
        this.fieldRepository = fieldRepository;
        this.playerRepository = playerRepository;
        this.createPadelDayService = createPadelDayService;
        this.padelDayRepository = padelDayRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping("/plan")
    public String plan(Model model){
        Optional<PadelDay> optionalPadelDay = padelDayRepository.getLast();
        boolean hasPlan = false;
        if(optionalPadelDay.isPresent()){
            model.addAttribute("padelDay", optionalPadelDay.get());
            hasPlan = !optionalPadelDay.get().getMatches().isEmpty();
        }
        model.addAttribute("hasPlan", hasPlan);
        return "admin/plan";
    }

    //Create a new plan for the curren PadelDay
    @PostMapping("/plan")
    public String postPlan(Model model,@RequestParam int id){
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);
        if(optionalPadelDay.isPresent()){
            PadelDay padelDay = optionalPadelDay.get();
            if(padelDay.getSignedUpPlayers().size() == padelDay.getFields().size()*4) {
                createPadelDayService.newPadelDayPlanning(padelDay);
            }
        }
        return "redirect:/admin/plan";
    }

    //Edit the plan
    @PostMapping("/planEdit")
    public String postPlanEdit(@ModelAttribute Team team, @RequestParam List<Integer> playerIds){
        Optional<Player> optionalPlayer1 = playerRepository.findById(playerIds.get(0));
        Optional<Player> optionalPlayer2 = playerRepository.findById(playerIds.get(1));
        if(optionalPlayer1.isPresent() && optionalPlayer2.isPresent()){
            Player player1 = optionalPlayer1.get();
            Player player2 = optionalPlayer2.get();

            team.setPlayers(List.of(player1, player2));
            teamRepository.save(team);
        }

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
        padelDayRepository.save(padelDay);
        return "redirect:/admin/plan";
    }
}
