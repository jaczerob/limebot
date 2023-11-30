package dev.jaczerob.limebot.discord.commands.commands;

import dev.jaczerob.discord.SlashCommand;
import dev.jaczerob.limebot.database.commands.CommandService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

public class DeleteCommandCommand extends SlashCommand {
    private final CommandService commandService;

    public DeleteCommandCommand(final CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public @NonNull String getName() {
        return "delete";
    }

    @Override
    public @NonNull String getDescription() {
        return "Deletes a command";
    }

    @Override
    public @NonNull List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "name", "The name of the command", true)
        );
    }

    @Override
    public @NonNull MessageCreateData execute(final @NonNull SlashCommandInteractionEvent event) {
        final String name = Objects.requireNonNull(event.getOption("name")).getAsString();

        final String content;
        if (this.commandService.removeCommand(name)) {
            content = "Command deleted";
        } else {
            content = "Command does not exist";
        }

        return new MessageCreateBuilder()
                .setContent(content)
                .build();
    }
}
