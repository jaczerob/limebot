package dev.jaczerob.limebot.database.reminders;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Integer> {

}
