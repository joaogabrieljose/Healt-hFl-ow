package dev.joaogj.Auth.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.joaogj.Auth.dto.Request.LoginRequest;
import dev.joaogj.Auth.dto.Request.RegisterUserRequest;
import dev.joaogj.Auth.dto.Response.LoginResponse;
import dev.joaogj.Auth.dto.Response.ResgisterUserResponse;
import dev.joaogj.Auth.entity.User;
import dev.joaogj.Auth.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping
public class AuthControllher {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthControllher(UserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        return null;
    }


    public ResponseEntity<ResgisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request){
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(request.password()) );
        newUser.setEmail(request.email());
        newUser.setName(request.name());

        repository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResgisterUserResponse(newUser.getName(), newUser.getEmail()));
    }
    


}
