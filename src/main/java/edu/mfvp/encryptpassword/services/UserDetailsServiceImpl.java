package edu.mfvp.encryptpassword.services;

import edu.mfvp.encryptpassword.repositories.UserRepository;
import edu.mfvp.encryptpassword.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = userRepository.findByUsername(username);
        if(usuario.isEmpty()) throw  new UsernameNotFoundException("User: " + username + " not found");

        return new UserDetailsImpl(usuario.get());
    }
}
