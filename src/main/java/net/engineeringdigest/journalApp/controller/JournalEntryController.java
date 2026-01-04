//package net.engineeringdigest.journalApp.controller;
//
//import net.engineeringdigest.journalApp.entity.JournalEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/_journal")
//public class JournalEntryController {
//
//    private Map<Long, JournalEntity> journalEntries = new HashMap<>();
//
//    @GetMapping
//    public List<JournalEntity> getAllEntries(){
//        return new ArrayList<>(journalEntries.values());
//    }
//
//    @GetMapping("/id/{id}")
//    public JournalEntity getEntry(@PathVariable Long id){
//        return journalEntries.get(id);
//    }
//
//    @PostMapping
//    public JournalEntity createEntry(@RequestBody JournalEntity newEntry){
//        journalEntries.put(newEntry.getId(), newEntry);
//        return newEntry;
//    }
//
//    @DeleteMapping("/id/{id}")
//    public JournalEntity deleteEntry(@PathVariable Long id){
//        return journalEntries.remove(id);
//    }
//
//    @PutMapping("/id/{id}")
//    public JournalEntity updateEntry(@PathVariable Long id, @RequestBody JournalEntity newEntry){
//        journalEntries.put(id, newEntry);
//        return newEntry;
//    }
//}
