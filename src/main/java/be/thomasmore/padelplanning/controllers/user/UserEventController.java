package be.thomasmore.padelplanning.controllers.user;

import be.thomasmore.padelplanning.model.ClubEvent;
import be.thomasmore.padelplanning.repositories.ClubEventRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
}
