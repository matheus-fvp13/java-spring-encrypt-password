package edu.mfvp.encryptpassword.controllers;

import edu.mfvp.encryptpassword.repositories.UserRepository;
import edu.mfvp.encryptpassword.schemas.UserSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserSchema>> listAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserSchema user, UriComponentsBuilder ucBuilder) {
        var newUser = userRepository.save(user);
        var uri = ucBuilder
                .path("/users/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
