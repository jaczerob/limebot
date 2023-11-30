package dev.jaczerob.limebot.discord.listeners;

import dev.jaczerob.limebot.database.commands.CommandService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class CommandListener extends ListenerAdapter {
    private static final Logger log = LoggerFactory.getLogger(CommandListener.class);

    private final String prefix;
    private final CommandService commandService;

    public CommandListener(
            @Value("${discord.prefix}") final String prefix,
            final CommandService commandService
    ) {
        this.prefix = prefix;
        this.commandService = commandService;
    }

    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;

        final String message = event.getMessage().getContentRaw();
        if (!message.startsWith(this.prefix))
            return;

        final String command = message.substring(prefix.length());
        log.info("Checking for command: {}", command);

        final Optional<String> optionalResponse = this.commandService.getResponse(command);
        if (optionalResponse.isEmpty())
            return;

        event.getChannel().sendMessage(optionalResponse.get()).setAllowedMentions(Collections.emptyList()).queue();
    }
}
