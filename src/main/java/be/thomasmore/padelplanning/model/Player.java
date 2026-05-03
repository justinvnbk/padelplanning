package be.thomasmore.padelplanning.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private boolean isAdmin;
    private char gender;
    private Date birthDate;
    private String selfEvaluation;
    private Integer pRanking;
    private String telephone;
    private String email;
    @Enumerated(EnumType.STRING)
    private PreferredPlayside preferredPlayside;
    private String profilePictureUrl;
    @ManyToMany(mappedBy = "players")
    private Collection<Team> teams;
//  @ManyToOne(fetch = FetchType.LAZY)
//  private PadelDay padelDay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSelfEvaluation() {
        return selfEvaluation;
    }

    public void setSelfEvaluation(String selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    public Integer getpRanking() {
        return pRanking;
    }

    public void setpRanking(Integer pRanking) {
        this.pRanking = pRanking;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PreferredPlayside getPreferredPlayside() {
        return preferredPlayside;
    }

    public void setPreferredPlayside(PreferredPlayside preferredPlayside) {
        this.preferredPlayside = preferredPlayside;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public Collection<Team> getTeams() {
        return teams;
    }

    public void setTeams(Collection<Team> teams) {
        this.teams = teams;
    }
}
