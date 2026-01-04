package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntity> getAllEntries(){
        return journalEntryService.getAll();
    }

    @GetMapping("/id/{id}")
    public JournalEntity getEntry(@PathVariable String id){
        return journalEntryService.findById(id).orElse(null);
    }

    @PostMapping
    public JournalEntity createEntry(@RequestBody JournalEntity newEntry){
        newEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(newEntry);
        return newEntry;
    }

    @DeleteMapping("/id/{id}")
    public boolean deleteEntry(@PathVariable String id){
        journalEntryService.deleteById(id);
        return true;
    }

    @PutMapping("/id/{id}")
    public JournalEntity updateEntry(@PathVariable String id, @RequestBody JournalEntity newEntry){
        JournalEntity old = journalEntryService.findById(id).orElse(null);
        if(old == null){
            return null;
        }

        if(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()){
            old.setTitle(newEntry.getTitle());
        }

        if(newEntry.getContent() != null && !newEntry.getContent().isEmpty()){
            old.setContent(newEntry.getContent());
        }
        journalEntryService.saveEntry(old);
        return old;
    }
}
