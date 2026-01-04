package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntity journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntity> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> findById(String id){
        return journalEntryRepository.findById(id);
    }

    public void deleteById(String id){
        journalEntryRepository.deleteById(id);
    }
}
