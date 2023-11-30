package dev.jaczerob.greendog.discord.commands.commands;

import dev.jaczerob.discord.SlashCommand;
import dev.jaczerob.greendog.database.commands.CommandService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.lang.NonNull;

import java.util.List;

public class ListCommandsCommand extends SlashCommand {
    private final CommandService commandService;

    public ListCommandsCommand(final CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public @NonNull String getName() {
        return "list";
    }

    @Override
    public @NonNull String getDescription() {
        return "Lists all commands";
    }

    @Override
    public @NonNull MessageCreateData execute(final @NonNull SlashCommandInteractionEvent event) {
        final List<String> commands = this.commandService.getCommandNames();
        final String response;

        if (commands.isEmpty()) {
            response = "There are no commands";
        } else {
            response = String.format("Here are the commands: %s", String.join(", ", commands));
        }

        return new MessageCreateBuilder()
                .setContent(response)
                .build();
    }
}
