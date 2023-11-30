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
public class RollCommand extends SlashCommand {
    @Override
    public @NonNull String getName() {
        return "roll";
    }

    @Override
    public @NonNull String getDescription() {
        return "Rolls between 1 and 100 or optional range";
    }

    @Override
    public @NonNull List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.INTEGER, "minimum", "The minimum roll", false).setMinValue(0).setMaxValue(Integer.MAX_VALUE - 1),
                new OptionData(OptionType.INTEGER, "maximum", "The maximum roll", false).setMinValue(1).setMaxValue(Integer.MAX_VALUE - 1)
        );
    }

    @Override
    public @NonNull MessageCreateData execute(final @NonNull SlashCommandInteractionEvent event) {
        final OptionMapping minimumOption = event.getOption("minimum");
        final OptionMapping maximumOption = event.getOption("maximum");

        long minimum;
        long maximum;

        if (minimumOption == null) {
            minimum = 1;
        } else {
            minimum = minimumOption.getAsLong();
        }

        if (maximumOption == null) {
            maximum = 100;
        } else {
            maximum = maximumOption.getAsLong();
        }

        if (minimum > maximum) {
            return new MessageCreateBuilder()
                    .addContent("Minimum cannot be greater than maximum!")
                    .build();
        }

        final long roll;

        if (minimum == maximum) {
            roll = minimum;
        } else {
            roll = ThreadLocalRandom.current().nextLong(minimum, maximum + 1);
        }

        return new MessageCreateBuilder()
                .addContent(String.format("From %d to %d, you rolled a %d", minimum, maximum, roll))
                .build();
    }
}
