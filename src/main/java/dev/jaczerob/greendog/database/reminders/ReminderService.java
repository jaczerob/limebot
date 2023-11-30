package dev.jaczerob.greendog.database.reminders;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReminderService {
    private final ReminderRepository reminderRepository;

    public ReminderService(final ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public void createReminder(final String message, final long userId, final long channelId, final Date date) {
        final Reminder reminder = new Reminder();
        reminder.setMessage(message);
        reminder.setUserId(userId);
        reminder.setChannelId(channelId);
        reminder.setReminderDate(date);
        this.reminderRepository.save(reminder);
    }

    public void deleteReminder(final int id) {
        this.reminderRepository.deleteById(id);
    }

    public List<Reminder> getReminders() {
        return this.reminderRepository.findAll();
    }
}
