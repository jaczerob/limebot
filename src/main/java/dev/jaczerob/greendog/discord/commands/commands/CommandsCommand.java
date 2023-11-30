package dev.jaczerob.greendog.discord.commands.commands;

import dev.jaczerob.discord.SlashCommand;
import dev.jaczerob.greendog.database.commands.CommandService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandsCommand extends SlashCommand {
    private final CommandService commandService;

    public CommandsCommand(final CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public @NonNull String getName() {
        return "commands";
    }

    @Override
    public @NonNull String getDescription() {
        return "Interfaces with commands";
    }

    @Override
    public @NonNull List<SlashCommand> getSubCommands() {
        return List.of(
                new GetCommandCommand(this.commandService),
                new DeleteCommandCommand(this.commandService),
                new AddCommandCommand(this.commandService),
                new ListCommandsCommand(this.commandService),
                new UpdateCommandCommand(this.commandService)
        );
    }

    @Override
    public @NonNull MessageCreateData execute(final @NonNull SlashCommandInteractionEvent slashCommandInteractionEvent) {
        return new MessageCreateBuilder()
                .addContent("Please use a subcommand!")
                .build();
    }
}
