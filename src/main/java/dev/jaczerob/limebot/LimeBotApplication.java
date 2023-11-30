package dev.jaczerob.limebot;

import net.dv8tion.jda.api.JDA;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ComponentScan("dev.jaczerob")
public class LimeBotApplication {
    public static void main(final String... args) throws InterruptedException {
        final ConfigurableApplicationContext context = new SpringApplicationBuilder(LimeBotApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        final JDA jda = context.getBean(JDA.class);
        jda.awaitShutdown();
    }
}
