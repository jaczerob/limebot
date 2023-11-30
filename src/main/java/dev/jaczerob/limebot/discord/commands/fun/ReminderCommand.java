package dev.jaczerob.limebot.discord.commands.fun;

import dev.jaczerob.discord.SlashCommand;
import dev.jaczerob.limebot.database.reminders.ReminderService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ReminderCommand extends SlashCommand {
    private static final Logger log = LoggerFactory.getLogger(ReminderCommand.class);
    private static final Pattern TIME_PATTERN = Pattern.compile("^(\\d+h)?(\\d{1,2}m)?$");

    private final ReminderService reminderService;

    public ReminderCommand(final ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @Override
    public @NonNull String getName() {
        return "reminder";
    }

    @Override
    public @NonNull String getDescription() {
        return "Sets a reminder (e.g. 1h30m)";
    }

    @Override
    public @NonNull List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "time", "The time to set the reminder for (e.g. 1h30m)", true),
                new OptionData(OptionType.STRING, "message", "The message to send when the reminder is up", true)
        );
    }

    @Override
    public @NonNull MessageCreateData execute(SlashCommandInteractionEvent event) {
        final String time = event.getOption("time").getAsString();
        final Matcher matcher = TIME_PATTERN.matcher(time);
        if (!matcher.matches()) {
            return new MessageCreateBuilder()
                    .addContent("Invalid time format. Write as xhym for x hours and y minutes.")
                    .build();
        }

        final String hoursString = matcher.group(1);
        final String minutesString = matcher.group(2);

        final long hours;
        final int minutes;
        if (hoursString == null) {
            hours = 0;
        } else {
            hours = Long.parseLong(hoursString.substring(0, hoursString.length() - 1));
        }

        if (minutesString == null) {
            minutes = 0;
        } else {
            minutes = Integer.parseInt(minutesString.substring(0, minutesString.length() - 1));
        }

        final long totalMinutes = hours * 60 + minutes;
        final Date remindDate = Date.from(Instant.now().plus(totalMinutes, ChronoUnit.MINUTES));

        final String message = event.getOption("message").getAsString();
        log.info("Setting reminder for {} at {}", message, remindDate);

        this.reminderService.createReminder(message, event.getUser().getIdLong(), event.getChannelIdLong(), remindDate);

        final String content = String.format("Reminder set for %s", remindDate);
        return new MessageCreateBuilder()
                .addContent(content)
                .build();
    }
}
