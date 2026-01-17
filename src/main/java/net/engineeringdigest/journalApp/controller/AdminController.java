package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<List<UserEntity>> getUsers(){
        try{
            List<UserEntity> users = userService.getUsers();
            return ResponseEntity.status(200).body(users);
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/create-admin")
    public ResponseEntity<UserEntity> addAdmin(@RequestBody UserEntity user){
        try{
            userService.saveAdminUser(user);
            return ResponseEntity.status(201).body(user);
        }
        catch(Exception e){
            log.error("Exception: ", e);
            return ResponseEntity.status(500).build();
        }
    }

}
