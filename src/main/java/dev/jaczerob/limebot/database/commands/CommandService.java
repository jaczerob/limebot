package dev.jaczerob.limebot.database.commands;

import dev.jaczerob.limebot.discord.listeners.CommandListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandService {
    private static final Logger log = LoggerFactory.getLogger(CommandListener.class);

    private final CommandRepository commandRepository;

    public CommandService(final CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public Optional<String> getResponse(final String name) {
        log.info("Getting command response for: {}", name);

        return this.commandRepository.findById(name).map(Command::getResponse);
    }

    public boolean addCommand(final String name, final String response) {
        log.info("Adding command: {} -> {}", name, response);

        if (this.commandRepository.existsById(name))
            return false;

        this.commandRepository.save(new Command(name, response));
        return true;
    }

    public boolean removeCommand(final String name) {
        log.info("Removing command: {}", name);

        if (!this.commandRepository.existsById(name))
            return false;

        this.commandRepository.deleteById(name);
        return true;
    }

    public boolean updateCommand(final String name, final String response) {
        log.info("Updating command: {} -> {}", name, response);

        if (!this.commandRepository.existsById(name))
            return false;

        this.commandRepository.save(new Command(name, response));
        return true;
    }

    public List<String> getCommandNames() {
        log.info("Getting command names");

        return this.commandRepository.findAll().stream().map(Command::getName).toList();
    }
}
