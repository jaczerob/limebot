package dev.jaczerob.limebot.discord.commands.fun;

import dev.jaczerob.discord.SlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class CoinflipCommand extends SlashCommand {
    @Override
    public @NonNull String getName() {
        return "coinflip";
    }

    @Override
    public @NonNull String getDescription() {
        return "Flips a coin";
    }

    @Override
    public @NonNull MessageCreateData execute(final @NonNull SlashCommandInteractionEvent event) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final int roll = random.nextInt(2);
        final String result = roll == 0 ? "heads" : "tails";

        return new MessageCreateBuilder()
                .setContent(String.format("You flipped a coin and got %s", result))
                .build();
    }
}
