package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

//    @GetMapping
//    public ResponseEntity<List<JournalEntity>> getAllEntries(){
//        List<JournalEntity> entries = journalEntryService.getAll();
//        return ResponseEntity.status(200).body(entries);
//    }

    @GetMapping
    public ResponseEntity<List<JournalEntity>> getAllEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userService.findByUsername(username);
        List<JournalEntity> journalList = user.getJournalEntries();
        return new ResponseEntity<>(journalList, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntity> getEntry(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(id.trim().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userService.findByUsername(username);
        List<JournalEntity> entry = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!entry.isEmpty()){
            Optional<JournalEntity> jEntry = journalEntryService.findById(id);
            if(jEntry.isPresent()){
                return new ResponseEntity<>(jEntry.get(),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
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


    @DeleteMapping("/{id}")
    public ResponseEntity<JournalEntity> deleteEntry(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(id.trim().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userService.findByUsername(username);
        List<JournalEntity> entry = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!entry.isEmpty()){
            Optional<JournalEntity> jEntry = journalEntryService.findById(id);
            if(jEntry.isPresent()){
                journalEntryService.deleteById(id, username);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntity> updateEntry(@PathVariable String id, @RequestBody JournalEntity newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(id.trim().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userService.findByUsername(username);
        List<JournalEntity> entry = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!entry.isEmpty()){
            Optional<JournalEntity> jEntry = journalEntryService.findById(id);
            if(jEntry.isPresent()){
                if(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()){
                    jEntry.get().setTitle(newEntry.getTitle());
                }

                if(newEntry.getContent() != null && !newEntry.getContent().isEmpty()){
                    jEntry.get().setContent(newEntry.getContent());
                }
                journalEntryService.saveEntry(jEntry.get());
                return new ResponseEntity<>(jEntry.get(),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
