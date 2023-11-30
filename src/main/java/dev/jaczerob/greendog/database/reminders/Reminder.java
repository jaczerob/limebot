package dev.jaczerob.greendog.database.reminders;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "reminders")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private long userId;
    private long channelId;
    private String message;

    @Column(name = "reminderDate", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reminderDate;

    public Reminder() {
    }

    public Reminder(final long userId, final long channelId, final String message, final Date reminderDate) {
        this.userId = userId;
        this.channelId = channelId;
        this.message = message;
        this.reminderDate = reminderDate;
    }

    public int getId() {
        return this.id;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

    public long getChannelId() {
        return this.channelId;
    }

    public void setChannelId(final long channelId) {
        this.channelId = channelId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Date getReminderDate() {
        return this.reminderDate;
    }

    public void setReminderDate(final Date reminderDate) {
        this.reminderDate = reminderDate;
    }
}
