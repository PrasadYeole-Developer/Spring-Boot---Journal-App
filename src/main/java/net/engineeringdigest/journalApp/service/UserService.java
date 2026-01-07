package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getUsers(){
        return userRepository.findAll();
    }

    public void saveUser(UserEntity user){
        userRepository.save(user);
    }

    public Optional<UserEntity> findById(String id){
        return userRepository.findById(id);
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }

}
