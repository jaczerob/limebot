package dev.jaczerob.greendog.discord.commands.birthday;

import dev.jaczerob.discord.SlashCommand;
import dev.jaczerob.greendog.database.birthdays.Birthday;
import dev.jaczerob.greendog.database.birthdays.BirthdayService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BirthdayCommand extends SlashCommand {
    private final BirthdayService birthdayService;

    public BirthdayCommand(final BirthdayService birthdayService) {
        this.birthdayService = birthdayService;
    }

    @Override
    public @NonNull String getDescription() {
        return "Gets your birthday!";
    }

    @Override
    public @NonNull String getName() {
        return "birthday";
    }

    @Override
    public @NonNull List<SlashCommand> getSubCommands() {
        return List.of(
                new RemoveBirthdayCommand(this.birthdayService),
                new ListBirthdaysCommand(this.birthdayService),
                new SetBirthdayCommand(this.birthdayService)
        );
    }

    @Override
    public @NonNull MessageCreateData execute(final @NonNull SlashCommandInteractionEvent slashCommandInteractionEvent) {
        final Optional<Birthday> birthday = this.birthdayService.getBirthday(slashCommandInteractionEvent.getUser().getIdLong());

        final String message = birthday
                .map(value -> String.format("Your birthday is %s/%s/%s", value.getBirthday().getMonthValue(), value.getBirthday().getDayOfMonth(), value.getBirthday().getYear()))
                .orElse("You don't have a birthday set!");

        return new MessageCreateBuilder()
                .addContent(message)
                .build();
    }
}
