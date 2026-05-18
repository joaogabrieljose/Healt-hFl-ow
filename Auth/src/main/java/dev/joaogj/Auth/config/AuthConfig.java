package dev.joaogj.Auth.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.joaogj.Auth.repository.UserRepository;

@Service
public class AuthConfig implements UserDetailsService{

    private final UserRepository repository;
    public AuthConfig(UserRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.findUserByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username));
    }
    
}
