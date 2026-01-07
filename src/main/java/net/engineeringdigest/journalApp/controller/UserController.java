package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getUsers(){
        List<UserEntity> users = userService.getUsers();
        if(users.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        try{
            return ResponseEntity.status(200).body(users);
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
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

    @PutMapping
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user){
        UserEntity oldUser = userService.findByUsername(user.getUserName());
        if(oldUser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(user.getUserName() != null && !user.getUserName().isEmpty()){
            oldUser.setUserName(user.getUserName());
        }

        if(user.getPassword() != null && !user.getPassword().isEmpty()){
            oldUser.setPassword(user.getPassword());
        }
        userService.saveUser(oldUser);
        return new ResponseEntity<>(oldUser,HttpStatus.OK);
    }

}
