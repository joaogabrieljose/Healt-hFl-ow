package dev.joaogj.Auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
public class ControllerUser {

    @GetMapping("/dashboard")
    public String useDashboard(){
        return "Usuário autenticado  (USER OU ADMIN acessa! )";
    }
    
}
