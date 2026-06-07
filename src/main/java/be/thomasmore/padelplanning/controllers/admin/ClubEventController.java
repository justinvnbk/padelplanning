package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.ClubEvent;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.ClubEventRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ClubEventController {

    private final ClubEventRepository clubEventRepository;
    private final PlayerRepository playerRepository;

    public ClubEventController(ClubEventRepository clubEventRepository, PlayerRepository playerRepository) {
        this.clubEventRepository = clubEventRepository;
        this.playerRepository = playerRepository;
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
    public String postNewEvent(@Valid @ModelAttribute("clubEvent") ClubEvent clubEvent,
                               BindingResult bindingResult) {
        validateEvent(clubEvent, bindingResult, 0);

        if (bindingResult.hasErrors()) {
            return "admin/newevent";
        }

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

    @PostMapping("/events/{eventId}/publish")
    public String publishEvent(@PathVariable Integer eventId) {
        Optional<ClubEvent> optionalClubEvent = clubEventRepository.findById(eventId);

        if (optionalClubEvent.isEmpty()) {
            return "redirect:/admin/events";
        }

        ClubEvent clubEvent = optionalClubEvent.get();
        clubEvent.setPublished(true);

        clubEventRepository.save(clubEvent);

        return "redirect:/admin/events/" + eventId;
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

    @PostMapping("/events/{eventId}/edit")
    public String updateEvent(@PathVariable Integer eventId,
                              @Valid @ModelAttribute("clubEvent") ClubEvent submittedEvent,
                              BindingResult bindingResult) {

        Optional<ClubEvent> optionalClubEvent = clubEventRepository.findById(eventId);

        if (optionalClubEvent.isEmpty()) {
            return "redirect:/admin/events";
        }

        ClubEvent existingEvent = optionalClubEvent.get();

        validateEvent(
                submittedEvent,
                bindingResult,
                existingEvent.getParticipants().size()
        );

        if (bindingResult.hasErrors()) {
            submittedEvent.setId(eventId);
            submittedEvent.setPublished(existingEvent.isPublished());
            submittedEvent.setParticipants(existingEvent.getParticipants());

            return "admin/editevent";
        }

        existingEvent.setTitle(submittedEvent.getTitle());
        existingEvent.setDescription(submittedEvent.getDescription());
        existingEvent.setLocation(submittedEvent.getLocation());
        existingEvent.setStartDateTime(submittedEvent.getStartDateTime());
        existingEvent.setEndDateTime(submittedEvent.getEndDateTime());
        existingEvent.setMaximumParticipants(submittedEvent.getMaximumParticipants());
        existingEvent.setPrice(submittedEvent.getPrice());

        clubEventRepository.save(existingEvent);

        return "redirect:/admin/events/" + eventId;
    }

    @GetMapping("/events/{eventId}/participants")
    public String eventParticipants(@PathVariable Integer eventId,
                                    @RequestParam(defaultValue = "") String searchTerm,
                                    Model model) {

        Optional<ClubEvent> optionalClubEvent = clubEventRepository.findById(eventId);

        if (optionalClubEvent.isEmpty()) {
            return "redirect:/admin/events";
        }

        List<Player> participants = playerRepository.findParticipantsByEventIdAndSearchTerm(
                eventId,
                searchTerm.trim()
        );

        model.addAttribute("clubEvent", optionalClubEvent.get());
        model.addAttribute("participants", participants);
        model.addAttribute("searchTerm", searchTerm);

        return "admin/eventparticipants";
    }

    private void validateEvent(ClubEvent clubEvent,
                               BindingResult bindingResult,
                               int currentParticipantCount) {

        if (clubEvent.getStartDateTime() != null
                && clubEvent.getEndDateTime() != null
                && !clubEvent.getEndDateTime().isAfter(clubEvent.getStartDateTime())) {

            bindingResult.rejectValue(
                    "endDateTime",
                    "invalid.endDateTime",
                    "De einddatum moet na de startdatum liggen."
            );
        }

        if (clubEvent.getMaximumParticipants() != null
                && clubEvent.getMaximumParticipants() < currentParticipantCount) {

            bindingResult.rejectValue(
                    "maximumParticipants",
                    "invalid.maximumParticipants",
                    "Het maximum kan niet lager zijn dan het huidige aantal deelnemers."
            );
        }
    }
}
