package dev.jaczerob.limebot.discord.commands.birthday;

import dev.jaczerob.discord.SlashCommand;
import dev.jaczerob.limebot.database.birthdays.Birthday;
import dev.jaczerob.limebot.database.birthdays.BirthdayService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class ListBirthdaysCommand extends SlashCommand {
    private final BirthdayService birthdayService;

    public ListBirthdaysCommand(final BirthdayService birthdayService) {
        this.birthdayService = birthdayService;
    }

    @Override
    public @NonNull String getName() {
        return "list";
    }

    @Override
    public @NonNull String getDescription() {
        return "Lists all birthdays";
    }

    @Override
    public @NonNull MessageCreateData execute(final @NonNull SlashCommandInteractionEvent event) {
        final List<Birthday> birthdays = this.birthdayService.getAllBirthdays();
        final List<String> formattedBirthdays = new ArrayList<>();

        birthdays.forEach(birthday -> {
            final String mention = String.format("<@%d>", birthday.getUserId());

            final String birthdayString;
            if (birthday.getBirthday().getYear() == 1776) {
                birthdayString = String.format("%s/%s", birthday.getBirthday().getMonthValue(), birthday.getBirthday().getDayOfMonth());
            } else {
                final int age = Period.between(birthday.getBirthday().toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
                birthdayString = String.format("%d/%d (%d)", birthday.getBirthday().getMonthValue(), birthday.getBirthday().getDayOfMonth(), age);
            }

            final String formattedBirthday = String.format("%s: %s", mention, birthdayString);
            formattedBirthdays.add(formattedBirthday);
        });

        return new MessageCreateBuilder()
                .addContent(String.format("Birthdays: \n%s", String.join("\n", formattedBirthdays)))
                .build();
    }
}
