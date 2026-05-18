package dev.joaogj.Auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class ControllerAdmin {


    @GetMapping(value = "/panel")
    public String adminPanel(){
        return "somente admin acessa!";
    }
    
}
