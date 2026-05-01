package be.thomasmore.padelplanning.controllers.user;

import be.thomasmore.padelplanning.model.Match;
import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class SignUpController {
    private final PadelDayRepository padelDayRepository;
    private final PlayerRepository playerRepository;

    public SignUpController(PadelDayRepository padelDayRepository, PlayerRepository playerRepository) {
        this.padelDayRepository = padelDayRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/signup")
    public String reserve(Model model) {
        Optional<PadelDay> optionalPadelDay = padelDayRepository.getLast();
        boolean hasPlan = false;
        if(optionalPadelDay.isPresent()){
            model.addAttribute("padelDay", optionalPadelDay.get());
            hasPlan = !optionalPadelDay.get().getMatches().isEmpty();
        }
        model.addAttribute("hasPlan", hasPlan);
        return "user/signup";
    }
    @PostMapping("/signup")
    public String postPlan(Model model,
                           @RequestParam int id,
                           Principal principal){
        Optional<PadelDay> optionalPadelDay = padelDayRepository.findById(id);
        Player player = playerRepository.findByEmail(principal.getName());

        if(optionalPadelDay.isPresent()){
            PadelDay padelDay = optionalPadelDay.get();

            Collection<Player> reservePlayers = padelDay.getReservedPlayers();
            Collection<Player> signedUpPlayers = padelDay.getSignedUpPlayers();

            reservePlayers.add(player);
            if(reservePlayers.size() == 4 && signedUpPlayers.size() < padelDay.getFields().size()*4){
                signedUpPlayers.addAll(reservePlayers);
                reservePlayers.clear();
            }
            padelDay.setSignedUpPlayers(signedUpPlayers);
            padelDay.setReservedPlayers(reservePlayers);
            padelDayRepository.save(padelDay);
        }
        return "redirect:/user/signup";
    }
}
