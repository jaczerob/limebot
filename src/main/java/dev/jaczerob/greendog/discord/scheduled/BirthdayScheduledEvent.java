package dev.jaczerob.greendog.discord.scheduled;

import dev.jaczerob.greendog.database.birthdays.Birthday;
import dev.jaczerob.greendog.database.birthdays.BirthdayService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Component
public class BirthdayScheduledEvent {
    private static final Logger log = LoggerFactory.getLogger(PuppyCornScheduledEvent.class);
    private static final long CHANNEL_ID = 1037144057757040732L;

    private final JDA jda;
    private final BirthdayService birthdayService;

    public BirthdayScheduledEvent(final JDA jda, final BirthdayService birthdayService) {
        this.jda = jda;
        this.birthdayService = birthdayService;
    }

    @Scheduled(cron = "0 0 12 * * *", zone = "America/New_York")
    public void sendBirthdays() {
        final TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
        if (channel == null) {
            log.error("Could not find channel with id {}", CHANNEL_ID);
            return;
        }

        final List<Birthday> birthdays = this.birthdayService.getAllBirthdays()
                .stream()
                .filter(birthday -> isToday(birthday.getBirthday()))
                .toList();

        if (birthdays.isEmpty()) {
            log.info("No birthdays today");
            return;
        }

        birthdays.forEach(birthday -> {
            final String mention = String.format("<@%s>", birthday.getUserId());

            final String birthdayMessage;
            if (birthday.getBirthday().getYear() != 1776) {
                final int age = Period.between(birthday.getBirthday().toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
                birthdayMessage = String.format("Happy birthday to %s, who is %d years old today!", mention, age);
            } else {
                birthdayMessage = String.format("Happy birthday to %s!", mention);
            }

            channel.sendMessage(birthdayMessage).queue();
        });
    }

    private static boolean isToday(final LocalDateTime birthday) {
        final int birthdayMonth = birthday.getMonthValue();
        final int birthdayDay = birthday.getDayOfMonth();

        final LocalDateTime now = LocalDateTime.now();
        final int todayMonth = now.getMonthValue();
        final int todayDay = now.getDayOfMonth();

        return birthdayMonth == todayMonth && birthdayDay == todayDay;
    }
}
