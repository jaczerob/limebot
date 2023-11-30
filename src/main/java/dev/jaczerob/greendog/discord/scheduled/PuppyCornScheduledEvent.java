package dev.jaczerob.greendog.discord.scheduled;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PuppyCornScheduledEvent {
    private static final Logger log = LoggerFactory.getLogger(PuppyCornScheduledEvent.class);
    private static final long CHANNEL_ID = 1037144057757040732L;

    private final JDA jda;

    public PuppyCornScheduledEvent(final JDA jda) {
        this.jda = jda;
    }

    @Scheduled(cron = "0 0 11 * * *", zone = "America/New_York")
    public void sendPuppyCorn() {
        final TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
        if (channel == null) {
            log.error("Could not find channel with id {}", CHANNEL_ID);
            return;
        }

        channel.sendMessage("gm fam the pup is up!").queue();
    }
}
