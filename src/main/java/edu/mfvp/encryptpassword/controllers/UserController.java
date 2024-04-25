package edu.mfvp.encryptpassword.controllers;

import edu.mfvp.encryptpassword.repositories.UserRepository;
import edu.mfvp.encryptpassword.schemas.UserSchema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<UserSchema>> listAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserSchema user, UriComponentsBuilder ucBuilder) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var newUser = userRepository.save(user);
        var uri = ucBuilder
                .path("/users/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/auth")
    public ResponseEntity<Boolean> validatePassword(@RequestParam String username,
                                                    @RequestParam String password) {
        Optional<UserSchema> user = this.userRepository.findByUsername(username);

        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        boolean valid = passwordEncoder.matches(password, user.get().getPassword());
        int status = valid ? HttpStatus.OK.value() : HttpStatus.UNAUTHORIZED.value();

        return ResponseEntity.status(status).body(valid);
    }
}
