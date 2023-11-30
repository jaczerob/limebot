package dev.jaczerob.greendog.database.birthdays;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Birthday {
    @Id
    private long userId;
    private LocalDateTime birthday;

    public Birthday() {
    }

    public Birthday(final long userId, final LocalDateTime birthday) {
        this.userId = userId;
        this.birthday = birthday;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

    public LocalDateTime getBirthday() {
        return this.birthday;
    }

    public void setBirthday(final LocalDateTime birthday) {
        this.birthday = birthday;
    }
}
