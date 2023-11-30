package dev.jaczerob.greendog.database.birthdays;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BirthdayService {
    private final BirthdayRepository birthdayRepository;

    public BirthdayService(final BirthdayRepository birthdayRepository) {
        this.birthdayRepository = birthdayRepository;
    }

    public Optional<Birthday> getBirthday(final long userId) {
        return this.birthdayRepository.findById(userId);
    }

    public void setBirthday(final long userId, final LocalDateTime date) {
        final Birthday birthday = new Birthday(userId, date);
        this.birthdayRepository.save(birthday);
    }

    public void deleteBirthday(final long userId) {
        this.birthdayRepository.deleteById(userId);
    }

    public List<Birthday> getAllBirthdays() {
        return this.birthdayRepository.findAll();
    }
}
