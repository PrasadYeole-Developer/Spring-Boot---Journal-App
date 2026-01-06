package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntity>> getAllEntries(){
        List<JournalEntity> journalList = journalEntryService.getAll();
        return new ResponseEntity<>(journalList, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntity> getEntry(@PathVariable String id){
        if(id.trim().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<JournalEntity> jEntry = journalEntryService.findById(id);
        if(jEntry.isPresent()){
            return new ResponseEntity<>(jEntry.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity newEntry){
        journalEntryService.saveEntry(newEntry);
        return ResponseEntity.status(201).body(newEntry);
        // OR
        // return ResponseEntity.status(HttpStatus.CREATED).body(newEntry); both works like this
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<JournalEntity> deleteEntry(@PathVariable String id){
        if(id.trim().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JournalEntity jEntry = journalEntryService.findById(id).orElse(null);
        if(jEntry == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        journalEntryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntity> updateEntry(@PathVariable String id, @RequestBody JournalEntity newEntry){
        if(id.trim().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JournalEntity old = journalEntryService.findById(id).orElse(null);
        if(old == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()){
            old.setTitle(newEntry.getTitle());
        }

        if(newEntry.getContent() != null && !newEntry.getContent().isEmpty()){
            old.setContent(newEntry.getContent());
        }
        journalEntryService.saveEntry(old);
        return new ResponseEntity<>(old,HttpStatus.OK);
    }
}
