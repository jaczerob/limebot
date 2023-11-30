package dev.jaczerob.greendog.discord.commands.birthday;

import dev.jaczerob.discord.SlashCommand;
import dev.jaczerob.greendog.database.birthdays.BirthdayService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Objects;

@Component
public class SetBirthdayCommand extends SlashCommand {
    private final BirthdayService birthdayService;

    public SetBirthdayCommand(final BirthdayService birthdayService) {
        this.birthdayService = birthdayService;
    }

    @Override
    public @NonNull String getName() {
        return "set";
    }

    @Override
    public @NonNull String getDescription() {
        return "Sets your birthday: MM/DD/YYYY or MM/DD";
    }

    @Override
    public @NonNull List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.INTEGER, "month", "Month of your birthday (1-12)", true).setMinValue(1).setMaxValue(12),
                new OptionData(OptionType.INTEGER, "day", "Day of your birthday (1-31)", true).setMinValue(1).setMaxValue(31),
                new OptionData(OptionType.INTEGER, "year", "Year of your birthday (optional)", false).setMinValue(1000).setMaxValue(3000)
        );
    }

    @Override
    public @NonNull MessageCreateData execute(final @NonNull SlashCommandInteractionEvent event) {
        final int day = Objects.requireNonNull(event.getOption("day")).getAsInt();
        final int month = Objects.requireNonNull(event.getOption("month")).getAsInt();
        final int year = event.getOption("year") == null ? 1776 : event.getOption("year").getAsInt();

        final long userId = event.getUser().getIdLong();
        final LocalDateTime birthday = LocalDateTime.of(year, month, day, 0, 0, 0);

        this.birthdayService.setBirthday(userId, birthday);

        return new MessageCreateBuilder()
                .setContent(String.format("Birthday set to %s %d", formatMonth(birthday.getMonth()), birthday.getDayOfMonth()))
                .build();
    }

    private static String formatMonth(final Month month) {
        return switch (month) {
            case JANUARY -> "January";
            case FEBRUARY -> "February";
            case MARCH -> "March";
            case APRIL -> "April";
            case MAY -> "May";
            case JUNE -> "June";
            case JULY -> "July";
            case AUGUST -> "August";
            case SEPTEMBER -> "September";
            case OCTOBER -> "October";
            case NOVEMBER -> "November";
            case DECEMBER -> "December";
        };
    }
}
