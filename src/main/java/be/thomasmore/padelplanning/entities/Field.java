package be.thomasmore.padelplanning.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean isOutside;
    @OneToMany(fetch = FetchType.LAZY)
    private Collection<Match> matches;
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<PadelDay> padelDays;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isOutside() {
        return isOutside;
    }

    public void setOutside(boolean outside) {
        isOutside = outside;
    }

    public Collection<Match> getMatches() {
        return matches;
    }

    public void setMatches(Collection<Match> matches) {
        this.matches = matches;
    }

    public Collection<PadelDay> getPadelDays() {
        return padelDays;
    }

    public void setPadelDays(Collection<PadelDay> padelDays) {
        this.padelDays = padelDays;
    }
}
