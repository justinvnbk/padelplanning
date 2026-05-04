package be.thomasmore.padelplanning.controllers.user;

import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.services.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class SignUpController {
    private final PadelDayRepository padelDayRepository;
    private final PlayerRepository playerRepository;
    private final NotificationService notificationService;

    public SignUpController(PadelDayRepository padelDayRepository, PlayerRepository playerRepository, NotificationService notificationService) {
        this.padelDayRepository = padelDayRepository;
        this.playerRepository = playerRepository;
        this.notificationService = notificationService;
    }

    @GetMapping("/signup")
    public String reserve(Model model,
                          Principal principal) {
        Optional<PadelDay> optionalPadelDay = padelDayRepository.getLast(LocalDateTime.now());
        boolean hasPlan = false;
        if(optionalPadelDay.isPresent()){
            PadelDay padelDay = optionalPadelDay.get();
            model.addAttribute("padelDay", padelDay);

            Player loggedPlayer = playerRepository.findByEmail(principal.getName());
            model.addAttribute("isSignedUp", padelDay.getSignedUpPlayers().contains(loggedPlayer) || padelDay.getReservedPlayers().contains(loggedPlayer));
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
            if(!padelDay.getSignedUpPlayers().contains(player) && !padelDay.getReservedPlayers().contains(player)){
                Collection<Player> reservePlayers = padelDay.getReservedPlayers();
                Collection<Player> signedUpPlayers = padelDay.getSignedUpPlayers();

                reservePlayers.add(player);
                if(reservePlayers.size() == 4 && signedUpPlayers.size() < padelDay.getFields().size()*4){
                    signedUpPlayers.addAll(reservePlayers);
                    //Send a notification to all reserve players that they moved to signed up players
                    notificationService.createNotification("Inschrijving padeldag: " + padelDay.getDate(),"Er zijn voldoende spelers voor uw inschrijving te verwerken voor de padeldag te " + padelDay.getDate(), reservePlayers);
                    reservePlayers.clear();
                }
                padelDay.setSignedUpPlayers(signedUpPlayers);
                padelDay.setReservedPlayers(reservePlayers);
                padelDayRepository.save(padelDay);
            }
        }
        return "redirect:/user/signup";
    }
}
