package be.thomasmore.padelplanning.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double averagePRanking;
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Player> players;
    @ManyToMany(mappedBy = "teams")
    private Collection<Match> matches;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAveragePRanking() {
        return averagePRanking;
    }

    public void setAveragePRanking(Double averagePRanking) {
        this.averagePRanking = averagePRanking;
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Collection<Player> players) {
        this.players = players;
    }

    public Collection<Match> getMatches() {
        return matches;
    }

    public void setMatches(Collection<Match> matches) {
        this.matches = matches;
    }
}
