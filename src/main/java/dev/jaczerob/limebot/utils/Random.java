package dev.jaczerob.limebot.utils;

import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Random {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Random.class);

    private static final double BOUND = 1000.0;

    public static boolean passesPercent(final double percent) {
        final double randomChance = ThreadLocalRandom.current().nextDouble(0.0, BOUND);
        final double randomPercentage = randomChance / BOUND * 100.0;

        log.info("Random chance: {}", randomChance);
        log.info("Random percentage: {}", randomPercentage);
        log.info("Target Percent: {}", percent);

        return randomPercentage <= percent;
    }

    public static <T> T choice(final List<T> choices) {
        final int index = ThreadLocalRandom.current().nextInt(0, choices.size());
        return choices.get(index);
    }
}
