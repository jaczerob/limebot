package dev.jaczerob.greendog.discord.scheduled;

import dev.jaczerob.greendog.database.reminders.Reminder;
import dev.jaczerob.greendog.database.reminders.ReminderService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class ReminderScheduledEvent {
    private static final Logger log = LoggerFactory.getLogger(ReminderScheduledEvent.class);
    private final ReminderService reminderService;
    private final JDA jda;

    public ReminderScheduledEvent(final ReminderService reminderService, final JDA jda) {
        this.reminderService = reminderService;
        this.jda = jda;
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void sendReminders() {
        final Date currentTime = new Date(System.currentTimeMillis());
        final List<Reminder> reminders = this.reminderService.getReminders();

        reminders.forEach(reminder -> {
            log.info("Checking reminder {} for user {} in channel {}", reminder.getId(), reminder.getUserId(), reminder.getChannelId());
            log.info("Reminder date: {}", reminder.getReminderDate().getTime());
            log.info("Current time: {}", System.currentTimeMillis());

            if (reminder.getReminderDate().after(currentTime))
                return;

            log.info("Sending reminder to user {} in channel {}", reminder.getUserId(), reminder.getChannelId());
            final TextChannel channel = this.jda.getTextChannelById(reminder.getChannelId());
            if (channel == null) {
                log.error("Could not find channel with id {}", reminder.getChannelId());
            } else {
                final String message = String.format("<@%s> %s", reminder.getUserId(), reminder.getMessage());
                channel.sendMessage(message).queue();
                log.info("Sent reminder to user {} in channel {}", reminder.getUserId(), reminder.getChannelId());
            }

            this.reminderService.deleteReminder(reminder.getId());
        });
    }
}
