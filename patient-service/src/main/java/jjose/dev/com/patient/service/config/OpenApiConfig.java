package jjose.dev.com.patient.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI patientServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HealthFlow - Patient Service API")
                        .description("API responsável pela gestão de pacientes, contactos, histórico clínico e estado clínico.")
                        .version("1.0.0"));
    }
}