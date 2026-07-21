package main.java.dev.joaogj.Auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI authServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HealthFlow - Auth Service API")
                        .description("API responsável pelo registo, autenticação de utilizadores e geração de token JWT.")
                        .version("1.0.0"));
    }
}