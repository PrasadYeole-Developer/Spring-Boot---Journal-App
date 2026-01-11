package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserEntity> getUsers(){
        return userRepository.findAll();
    }

    public void saveUser(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public void saveNewUser(UserEntity user){
        userRepository.save(user);
    }

    public Optional<UserEntity> findById(String id){
        return userRepository.findById(id);
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }

    public UserEntity findByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
