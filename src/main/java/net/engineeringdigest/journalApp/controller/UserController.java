package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getUsers(){
        try{
            List<UserEntity> users = userService.getUsers();
            return ResponseEntity.status(200).body(users);
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("update-user")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            UserEntity oldUser = userService.findByUsername(username);
            if(oldUser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(user.getUsername() != null && !user.getUsername().isEmpty()){
            oldUser.setUsername(user.getUsername());
            }
            if(user.getPassword() != null && !user.getPassword().isEmpty()){
            oldUser.setPassword(user.getPassword());
            }
            userService.saveUser(oldUser);
            return new ResponseEntity<>(oldUser,HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("delete-user")
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userRepository.deleteByUsername(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
