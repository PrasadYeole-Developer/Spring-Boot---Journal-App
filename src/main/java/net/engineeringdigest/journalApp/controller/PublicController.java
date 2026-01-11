package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user){
        try{
            userService.saveUser(user);
            return ResponseEntity.status(201).body(user);
        }
        catch(Exception e){
            log.error("Exception: ", e);
            return ResponseEntity.status(500).build();
        }
    }

}
