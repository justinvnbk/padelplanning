package be.thomasmore.padelplanning.entities;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Collection;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Double pRankingDifference;
    private LocalTime timeSlot;
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Team> teams;
    @ManyToMany(mappedBy = "matches")
    private Collection<PadelDay> padelDays;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getpRankingDifference() {
        return pRankingDifference;
    }

    public void setpRankingDifference(Double pRankingDifference) {
        this.pRankingDifference = pRankingDifference;
    }

    public LocalTime getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(LocalTime timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Collection<Team> getTeams() {
        return teams;
    }

    public void setTeams(Collection<Team> teams) {
        this.teams = teams;
    }

    public Collection<PadelDay> getPadelDays() {
        return padelDays;
    }

    public void setPadelDays(Collection<PadelDay> padelDays) {
        this.padelDays = padelDays;
    }
}
