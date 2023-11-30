package dev.jaczerob.limebot.discord.listeners;

import dev.jaczerob.limebot.utils.Random;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RandomListener extends ListenerAdapter {
    private static final List<String> RANDOM_GIFS = List.of(
            "https://tenor.com/view/toontown-quake-ttcc-gif-4094206234895749957",
            "https://tenor.com/view/pancake-squad-gif-25311486",
            "https://media.discordapp.net/attachments/942614353268588584/1144071453621358592/caption.gif",
            "https://tenor.com/view/by-the-nine-im-tweakin-elder-scrolls-skyrim-gif-25519944",
            "https://tenor.com/view/garten-of-banban-skibidi-toilet-malcolm-in-the-middle-huggy-wuggy-gif-17724442227496295988",
            "https://media.discordapp.net/attachments/758503498730962944/1151439369945743391/yourquakesir.gif",
            "https://media.discordapp.net/attachments/942614353268588584/1151276845979934730/caption.gif",
            "https://tenor.com/view/lets-go-mover-mover-and-mover-and-shaker-stamp-gif-18636458",
            "https://tenor.com/view/corporate-clash-toontown-pacesetter-jump-if-you-like-men-jump-gif-27272369"
    );

    private static final List<Emoji> RANDOM_EMOJIS = List.of(
            Emoji.fromUnicode("\uD83E\uDD13"),
            Emoji.fromUnicode("\uD83D\uDD95"),
            Emoji.fromCustom("TrollSpin", 1170086957091672154L, true),
            Emoji.fromCustom("jacky", 1167640093574565888L, false),
            Emoji.fromUnicode("\uD83E\uDEF6"),
            Emoji.fromCustom("zzzgarfield", 1170558712969175111L, false)
    );

    @Override
    public void onMessageReceived(final @NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;

        final boolean doSendRandomGif = Random.passesPercent(0.1);
        final boolean doReactRandomEmoji = Random.passesPercent(1);

        if (doSendRandomGif) {
            final String randomGif = Random.choice(RANDOM_GIFS);
            event.getChannel().sendMessage(randomGif).queue();
        }

        if (doReactRandomEmoji) {
            final Emoji randomEmoji = Random.choice(RANDOM_EMOJIS);
            event.getMessage().addReaction(randomEmoji).queue();
        }
    }
}
