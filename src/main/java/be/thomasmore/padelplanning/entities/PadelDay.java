package be.thomasmore.padelplanning.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
public class PadelDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime date;
    private int numberOfMatches;

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Match> matches;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "padelDays")
    private Collection<Field> fields;

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Player> signedUpPlayers;

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Player> reservedPlayers;

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

    public Collection<Match> getMatches() {
        return matches;
    }

    public void setMatches(Collection<Match> matches) {
        this.matches = matches;
    }

    public Collection<Field> getFields() {
        return fields;
    }

    public void setFields(Collection<Field> fields) {
        this.fields = fields;
    }

    public Collection<Player> getSignedUpPlayers() {
        return signedUpPlayers;
    }

    public void setSignedUpPlayers(Collection<Player> signedUpPlayers) {
        this.signedUpPlayers = signedUpPlayers;
    }

    public Collection<Player> getReservedPlayers() {
        return reservedPlayers;
    }

    public void setReservedPlayers(Collection<Player> reservedPlayers) {
        this.reservedPlayers = reservedPlayers;
    }
}
