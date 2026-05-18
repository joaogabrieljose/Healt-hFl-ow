package dev.joaogj.Auth.dto.Request;

import dev.joaogj.Auth.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(
        @NotEmpty(message = "nome é obrigatório") String name,
        @NotEmpty(message = "email é obrigatório") String email,
        @NotEmpty(message = "password é obrigatória") String password,
        Role role
) {
}