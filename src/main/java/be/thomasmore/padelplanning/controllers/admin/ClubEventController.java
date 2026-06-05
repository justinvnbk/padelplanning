package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.ClubEvent;
import be.thomasmore.padelplanning.repositories.ClubEventRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ClubEventController {

    private final ClubEventRepository clubEventRepository;

    public ClubEventController(ClubEventRepository clubEventRepository) {
        this.clubEventRepository = clubEventRepository;
    }

    @GetMapping("/events")
    public String events(Model model) {
        List<ClubEvent> clubEvents = clubEventRepository.findAllOrdered();

        model.addAttribute("clubEvents", clubEvents);

        return "admin/events";
    }

    @GetMapping("/newevent")
    public String newEvent(Model model) {
        ClubEvent clubEvent = new ClubEvent();

        clubEvent.setStartDateTime(LocalDateTime.now().plusDays(7));
        clubEvent.setEndDateTime(LocalDateTime.now().plusDays(7).plusHours(2));

        model.addAttribute("clubEvent", clubEvent);

        return "admin/newevent";
    }

    @PostMapping("/newevent")
    public String postNewEvent (ClubEvent clubEvent) {
        clubEventRepository.save(clubEvent);

        return "redirect:/admin/events";
    }

    @GetMapping("/events/{eventId}")
    public String eventDetails (Model model, @PathVariable Integer eventId) {
        Optional<ClubEvent> optionalClubEvent = clubEventRepository.findById(eventId);

        if (optionalClubEvent.isPresent()) {
            model.addAttribute("clubEvent", optionalClubEvent.get());
        } else {
            model.addAttribute("clubEvent", null);
        }

        return "admin/eventdetails";
    }

    @PostMapping("/events/{eventId}/delete")
    public String deleteEvent (@PathVariable Integer eventId) {

        if (clubEventRepository.existsById(eventId)) {
            clubEventRepository.deleteById(eventId);
        }

        return "redirect:/admin/events";
    }

    @GetMapping("/events/{eventId}/edit")
    public String editEvent (Model model, @PathVariable Integer eventId) {
        Optional<ClubEvent> optionalClubEvent = clubEventRepository.findById(eventId);

        if (optionalClubEvent.isEmpty()) {
            return "redirect:/admin/events";
        }

        model.addAttribute("clubEvent", optionalClubEvent.get());

        return "admin/editevent";
    }
}
