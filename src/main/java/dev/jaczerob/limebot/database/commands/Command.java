package dev.jaczerob.limebot.database.commands;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "commands")
public class Command {
    @Id
    private String name;

    @Column(length = 4000)
    private String response;

    public Command() {
    }

    public Command(final String name, final String response) {
        this.name = name;
        this.response = response;
    }

    public String getName() {
        return this.name;
    }

    public String getResponse() {
        return this.response;
    }
}
