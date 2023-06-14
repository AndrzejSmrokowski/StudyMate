package com.studymate.domain.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<User> findByUserId(String userId);
    Optional<User> findByEmailVerificationTokenToken(String token);


}
