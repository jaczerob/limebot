package dev.jaczerob.greendog.discord.commands.commands;

import dev.jaczerob.discord.SlashCommand;
import dev.jaczerob.greendog.database.commands.CommandService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

public class UpdateCommandCommand extends SlashCommand {
    private final CommandService commandService;

    public UpdateCommandCommand(final CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public @NonNull String getName() {
        return "update";
    }

    @Override
    public @NonNull String getDescription() {
        return "Updates a command";
    }

    @Override
    public @NonNull List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "name", "The name of the command", true),
                new OptionData(OptionType.STRING, "response", "The response of the command", true)
        );
    }

    @Override
    public @NonNull MessageCreateData execute(final @NonNull SlashCommandInteractionEvent event) {
        final String name = Objects.requireNonNull(event.getOption("name")).getAsString();
        final String response = Objects.requireNonNull(event.getOption("response")).getAsString();

        final String content;
        if (this.commandService.updateCommand(name, response)) {
            content = "Command updated";
        } else {
            content = "Command does not exist";
        }

        return new MessageCreateBuilder()
                .setContent(content)
                .build();
    }
}
