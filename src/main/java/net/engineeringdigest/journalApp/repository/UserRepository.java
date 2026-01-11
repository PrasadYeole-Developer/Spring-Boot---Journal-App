package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByUsername(String username);
    void deleteByUsername(String username);
}
