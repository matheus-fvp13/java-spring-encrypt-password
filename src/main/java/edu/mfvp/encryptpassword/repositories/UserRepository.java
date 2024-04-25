package edu.mfvp.encryptpassword.repositories;

import edu.mfvp.encryptpassword.schemas.UserSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserSchema, Long> {
    Optional<UserSchema> findByUsername(String username);
}
