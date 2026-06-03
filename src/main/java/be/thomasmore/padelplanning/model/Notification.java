package be.thomasmore.padelplanning.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String message;
    private Integer padelDayId;
    private LocalDateTime dateTime;

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Player> recipients;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPadelDayId() {return padelDayId;}

    public void setPadelDayId(Integer padelDayId) {this.padelDayId = padelDayId;}

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Collection<Player> getRecipients() {
        return recipients;
    }

    public void setRecipients(Collection<Player> recipients) {
        this.recipients = recipients;
    }
}
