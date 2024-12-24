package com.poc.authservice.repo;


import com.poc.authservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsernameAndCodiceAbi(String username, String codiceAbi);
}
