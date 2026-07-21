package jjose.dev.com.scheduling.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI schedulingServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HealthFlow - Scheduling Service API")
                        .description("API responsável pela gestão de consultas, agendamentos e histórico de estados das consultas.")
                        .version("1.0.0"));
    }
}