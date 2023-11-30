package dev.jaczerob.limebot.discord.commands.birthday;

import dev.jaczerob.discord.SlashCommand;
import dev.jaczerob.limebot.database.birthdays.BirthdayService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.lang.NonNull;

public class RemoveBirthdayCommand extends SlashCommand {
    private final BirthdayService birthdayService;

    public RemoveBirthdayCommand(final BirthdayService birthdayService) {
        this.birthdayService = birthdayService;
    }

    @Override
    public @NonNull String getName() {
        return "remove";
    }

    @Override
    public @NonNull String getDescription() {
        return "Removes your birthday";
    }

    @Override
    public @NonNull MessageCreateData execute(final @NonNull SlashCommandInteractionEvent event) {
        final long userId = event.getUser().getIdLong();
        this.birthdayService.deleteBirthday(userId);
        return new MessageCreateBuilder()
                .addContent("Birthday removed")
                .build();
    }
}
