package be.thomasmore.padelplanning.controllers.user;

import be.thomasmore.padelplanning.model.ClubEvent;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.ClubEventRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserEventController {
    private final ClubEventRepository clubEventRepository;
    private final PlayerRepository playerRepository;

    public UserEventController(ClubEventRepository clubEventRepository, PlayerRepository playerRepository) {
        this.clubEventRepository = clubEventRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/events")
    public String events (Model model) {
        List<ClubEvent> clubEvents = clubEventRepository.findUpcomingPublishedEvents();

        model.addAttribute("clubEvents", clubEvents);

        return "user/events";
    }

    @GetMapping("/events/{eventId}")
    public String eventDetails(@PathVariable Integer eventId, Model model, Principal principal) {
        Optional<ClubEvent> optionalClubEvent = clubEventRepository.findById(eventId);

        if (optionalClubEvent.isEmpty() || !optionalClubEvent.get().isPublished()) {
            return "redirect:/user/events";
        }

        ClubEvent clubEvent = optionalClubEvent.get();
        Player loggedPlayer = playerRepository.findByEmail(principal.getName());

        boolean isRegistered = clubEvent.getParticipants().contains(loggedPlayer);

        boolean isFull = clubEvent.getMaximumParticipants() != null
                && clubEvent.getParticipants().size() >= clubEvent.getMaximumParticipants();

        model.addAttribute("clubEvent", clubEvent);
        model.addAttribute("isRegistered", isRegistered);
        model.addAttribute("isFull", isFull);

        return "user/eventdetails";
    }
}
