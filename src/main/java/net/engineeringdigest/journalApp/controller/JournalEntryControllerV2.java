package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntity>> getAllEntries(){
        List<JournalEntity> entries = journalEntryService.getAll();
        return ResponseEntity.status(200).body(entries);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<JournalEntity>> getAllEntriesOfUser(@PathVariable String username){
        UserEntity user = userService.findByUsername(username);
        List<JournalEntity> journalList = user.getJournalEntries();
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

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity newEntry, @PathVariable String username){
        try {
            journalEntryService.saveEntry(newEntry, username);
            return ResponseEntity.status(201).body(newEntry);
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseEntity.status(500).build();
        }
        // OR
        // return ResponseEntity.status(HttpStatus.CREATED).body(newEntry); both works like this
    }


    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<JournalEntity> deleteEntry(@PathVariable String id, @PathVariable String username){
        if(id.trim().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JournalEntity jEntry = journalEntryService.findById(id).orElse(null);
        if(jEntry == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        journalEntryService.deleteById(id, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{username}/{id}")
    public ResponseEntity<JournalEntity> updateEntry(@PathVariable String id, @RequestBody JournalEntity newEntry, @PathVariable String username){
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
