package jjose.dev.com.doctor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI doctorServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HealthFlow - Doctor Service API")
                        .description("API responsável pela gestão de especialidades, médicos e horários dos médicos.")
                        .version("1.0.0"));
    }
}