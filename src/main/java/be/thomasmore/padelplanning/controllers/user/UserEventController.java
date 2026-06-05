package be.thomasmore.padelplanning.controllers.user;

import be.thomasmore.padelplanning.model.ClubEvent;
import be.thomasmore.padelplanning.repositories.ClubEventRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserEventController {
    private final ClubEventRepository clubEventRepository;

    public UserEventController(ClubEventRepository clubEventRepository) {
        this.clubEventRepository = clubEventRepository;
    }

    @GetMapping("/events")
    public String events (Model model) {
        List<ClubEvent> clubEvents = clubEventRepository.findUpcomingPublishedEvents();

        model.addAttribute("clubEvents", clubEvents);

        return "user/events";
    }

    @GetMapping("/events/{eventId}")
    public String eventDetails(@PathVariable Integer eventId, Model model) {
        Optional<ClubEvent> optionalClubEvent = clubEventRepository.findById(eventId);

        if (optionalClubEvent.isEmpty() || !optionalClubEvent.get().isPublished()) {
            return "redirect:/user/events";
        }

        model.addAttribute("clubEvent", optionalClubEvent.get());

        return "user/eventdetails";
    }
}
