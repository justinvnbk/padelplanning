package be.thomasmore.padelplanning.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private boolean isApproved;
    private char gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    private SelfEvaluation selfEvaluation;
    private Integer pRanking;
    private String telephone;
    private String email;
    private boolean hasUnseenNotifications;

    @Transient
    private String password;

    @Enumerated(EnumType.STRING)
    private PreferredPlayside preferredPlayside;

    private String profilePictureUrl;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "players")
    private Collection<Team> teams;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "recipients")
    private List<Notification> notifications;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<PadelDay> payedPadelDays;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<PadelDay> confirmedPayedPadelDays;

    @ManyToMany
    private List<ClubEvent> paidClubEvents = new ArrayList<>();

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

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
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

    public SelfEvaluation getSelfEvaluation() {
        return selfEvaluation;
    }

    public void setSelfEvaluation(SelfEvaluation selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    public Integer getpRanking() {
        return pRanking;
    }

    public void setpRanking(Integer pRanking) {
        this.pRanking = pRanking;
    }

    public Integer getEffectivePRanking() {
        if (pRanking != null) {
            return pRanking;
        }

        if (gender == 'F') {
            return 50;
        }

        if (gender == 'M') {
            return 100;
        }

        return 0;
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

    public boolean hasUnseenNotifications() {
        return hasUnseenNotifications;
    }

    public void setHasUnseenNotifications(boolean hasUnseenNotifications) {
        this.hasUnseenNotifications = hasUnseenNotifications;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<PadelDay> getPayedPadelDays() {
        return payedPadelDays;
    }

    public void setPayedPadelDays(List<PadelDay> payedPadelDays) {
        this.payedPadelDays = payedPadelDays;
    }

    public List<PadelDay> getConfirmedPayedPadelDays() {
        return confirmedPayedPadelDays;
    }

    public void setConfirmedPayedPadelDays(List<PadelDay> confirmedPayedPadelDays) {
        this.confirmedPayedPadelDays = confirmedPayedPadelDays;
    }

    public boolean isHasUnseenNotifications() {
        return hasUnseenNotifications;
    }

    public List<ClubEvent> getPaidClubEvents() {
        return paidClubEvents;
    }

    public void setPaidClubEvents(List<ClubEvent> paidClubEvents) {
        this.paidClubEvents = paidClubEvents;
    }
}
