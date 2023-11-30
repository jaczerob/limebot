package dev.jaczerob.greendog.database.commands;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, String> {
}
