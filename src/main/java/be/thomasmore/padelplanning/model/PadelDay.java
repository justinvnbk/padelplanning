package be.thomasmore.padelplanning.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class PadelDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime date;
    private int numberOfMatches;
    private boolean isPublished = false;

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Match> matches;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Field> fields;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Player> signedUpPlayers;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Player> reservedPlayers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public void setNumberOfMatches(int numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public Collection<Match> getMatches() {
        return matches;
    }

    public void setMatches(Collection<Match> matches) {
        this.matches = matches;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Player> getSignedUpPlayers() {
        return signedUpPlayers;
    }

    public void setSignedUpPlayers(List<Player> signedUpPlayers) {
        this.signedUpPlayers = signedUpPlayers;
    }

    public List<Player> getReservedPlayers() {
        return reservedPlayers;
    }

    public void setReservedPlayers(List<Player> reservedPlayers) {
        this.reservedPlayers = reservedPlayers;
    }

    public List<LocalTime> getUniqueTimeSlots() {
        return matches.stream()
                .map(Match::getTimeSlot)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
