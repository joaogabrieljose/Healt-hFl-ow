package dev.joaogj.Auth.dto.Request;

import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(@NotEmpty(message = "nome é obrigatorio") String name, 
                                 @NotEmpty(message = "email é obrigatorio") String email, 
                                @NotEmpty(message = "password é obrigatorio")String password) {
    
}
