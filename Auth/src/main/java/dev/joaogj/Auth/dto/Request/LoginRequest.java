package dev.joaogj.Auth.dto.Request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(@NotEmpty(message = "email e obrigatorio") String email,@NotEmpty(message = "password obrigatorio") String password) {

 }
