package dev.jaczerob.limebot.database.birthdays;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BirthdayRepository extends JpaRepository<Birthday, Long> {
}
