package dev.jaczerob.greendog.discord.commands;

import dev.jaczerob.discord.MessageContextCommand;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PinMessageContextCommand extends MessageContextCommand {
    @Override
    public @NonNull String getName() {
        return "pin";
    }

    @Override
    public @NonNull List<Long> getRequiredChannels() {
        return List.of(1158627610234982460L, 1160030798142001243L, 1175350413545971772L);
    }

    @Override
    public boolean isEphemeral() {
        return true;
    }

    @Override
    public @NonNull MessageCreateData execute(final @NonNull MessageContextInteractionEvent messageContextInteractionEvent) {
        messageContextInteractionEvent.getTarget().pin().queue();
        return new MessageCreateBuilder()
                .setContent("Message pinned")
                .build();
    }
}
