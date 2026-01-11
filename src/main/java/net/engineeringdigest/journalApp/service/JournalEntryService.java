package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntity journalEntry, String username){
        UserEntity user = userService.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntity entry = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(entry);
        userService.saveUser(user);
    }

    public void saveEntry(JournalEntity journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntity> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> findById(String id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteById(String id, String username){
        UserEntity user = userService.findByUsername(username);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }
}
