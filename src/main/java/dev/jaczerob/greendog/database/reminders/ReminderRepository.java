package dev.jaczerob.greendog.database.reminders;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Integer> {

}
