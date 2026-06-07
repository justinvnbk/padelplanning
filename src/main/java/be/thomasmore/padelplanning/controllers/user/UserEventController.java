package be.thomasmore.padelplanning.controllers.user;

import be.thomasmore.padelplanning.model.ClubEvent;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.ClubEventRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String events(Model model, Principal principal) {
        List<ClubEvent> clubEvents = clubEventRepository.findUpcomingPublishedEvents();

        Player loggedPlayer = playerRepository.findByEmail(principal.getName());

        List<ClubEvent> yourEvents = clubEvents.stream()
                .filter(clubEvent ->
                        clubEvent.getParticipants().contains(loggedPlayer))
                .toList();

        List<ClubEvent> otherEvents = clubEvents.stream()
                .filter(clubEvent ->
                        !clubEvent.getParticipants().contains(loggedPlayer))
                .toList();

        model.addAttribute("yourEvents", yourEvents);
        model.addAttribute("otherEvents", otherEvents);
        model.addAttribute("loggedPlayer", loggedPlayer);

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
        boolean isFull = clubEvent.getMaximumParticipants() != null && clubEvent.getParticipants().size() >= clubEvent.getMaximumParticipants();
        boolean hasPaid = loggedPlayer.getPaidClubEvents().contains(clubEvent);

        model.addAttribute("clubEvent", clubEvent);
        model.addAttribute("isRegistered", isRegistered);
        model.addAttribute("isFull", isFull);
        model.addAttribute("hasPaid", hasPaid);

        return "user/eventdetails";
    }

    @PostMapping("/events/{eventId}/register")
    public String registerForEvent(@PathVariable Integer eventId,
                                   Principal principal,
                                   RedirectAttributes redirectAttributes) {

        Optional<ClubEvent> optionalClubEvent = clubEventRepository.findById(eventId);

        if (optionalClubEvent.isEmpty() || !optionalClubEvent.get().isPublished()) {
            return "redirect:/user/events";
        }

        ClubEvent clubEvent = optionalClubEvent.get();
        Player loggedPlayer = playerRepository.findByEmail(principal.getName());

        boolean isFull = clubEvent.getMaximumParticipants() != null
                && clubEvent.getParticipants().size() >= clubEvent.getMaximumParticipants();

        if (clubEvent.getParticipants().contains(loggedPlayer)) {
            redirectAttributes.addFlashAttribute(
                    "alertWarning",
                    "U bent al ingeschreven voor dit evenement."
            );
        } else if (isFull) {
            redirectAttributes.addFlashAttribute(
                    "alertDanger",
                    "Dit evenement is volzet."
            );
        } else {
            clubEvent.getParticipants().add(loggedPlayer);
            clubEventRepository.save(clubEvent);

            redirectAttributes.addFlashAttribute(
                    "alertSuccess",
                    "U bent succesvol ingeschreven voor het evenement."
            );
        }

        return "redirect:/user/events/" + eventId;
    }

    @PostMapping("/events/{eventId}/unregister")
    public String unregisterFromEvent(@PathVariable Integer eventId,
                                      Principal principal,
                                      RedirectAttributes redirectAttributes) {

        Optional<ClubEvent> optionalClubEvent = clubEventRepository.findById(eventId);

        if (optionalClubEvent.isEmpty()) {
            return "redirect:/user/events";
        }

        ClubEvent clubEvent = optionalClubEvent.get();
        Player loggedPlayer = playerRepository.findByEmail(principal.getName());

        if (loggedPlayer.getPaidClubEvents().contains(clubEvent)) {
            redirectAttributes.addFlashAttribute(
                    "alertWarning",
                    "U kunt zich na betaling niet meer uitschrijven."
            );

            return "redirect:/user/events/" + eventId;
        }

        if (clubEvent.getParticipants().remove(loggedPlayer)) {
            clubEventRepository.save(clubEvent);

            redirectAttributes.addFlashAttribute(
                    "alertInfo",
                    "U bent succesvol uitgeschreven voor het evenement."
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "alertWarning",
                    "U was niet ingeschreven voor dit evenement."
            );
        }

        return "redirect:/user/events/" + eventId;
    }
}
