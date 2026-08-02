package pt.com.jjose.gatway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pt.com.jjose.gatway.security.JwtAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // ==========================
                        // ROTAS PÚBLICAS
                        // ==========================
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/register").permitAll()

                        // ==========================
                        // ACTUATOR / PROMETHEUS
                        // ==========================
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/actuator/prometheus").permitAll()
                        .requestMatchers("/actuator/prometheus/**").permitAll()

                        // ==========================
                        // SWAGGER DO API GATEWAY
                        // ==========================
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/api-docs/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()

                        // ==========================
                        // SWAGGER DOS MICROSERVIÇOS
                        // ==========================
                        .requestMatchers("/auth-docs/**").permitAll()
                        .requestMatchers("/patient-docs/**").permitAll()
                        .requestMatchers("/doctor-docs/**").permitAll()
                        .requestMatchers("/scheduling-docs/**").permitAll()
                        .requestMatchers("/triage-docs/**").permitAll()
                        .requestMatchers("/notification-docs/**").permitAll()
                        .requestMatchers("/audit-docs/**").permitAll()

                        // ==========================
                        // AUDITORIA
                        // Apenas ADMIN
                        // ==========================
                        .requestMatchers("/api/audit-logs/**").hasRole("ADMIN")

                        // ==========================
                        // NOTIFICAÇÕES
                        // USER e ADMIN podem consultar
                        // ==========================
                        .requestMatchers(HttpMethod.GET, "/api/notifications/**")
                        .hasAnyRole("USER", "ADMIN")

                        // ==========================
                        // PACIENTES
                        // USER e ADMIN podem consultar
                        // Apenas ADMIN pode criar, editar e apagar
                        // ==========================
                        .requestMatchers(HttpMethod.GET, "/api/patients/**")
                        .hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/patients/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/patients/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/patients/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // MÉDICOS
                        // USER e ADMIN podem consultar
                        // Apenas ADMIN pode criar, editar e apagar
                        // ==========================
                        .requestMatchers(HttpMethod.GET, "/api/doctors/**")
                        .hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/doctors/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/doctors/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/doctors/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // ESPECIALIDADES
                        // USER e ADMIN podem consultar
                        // Apenas ADMIN pode criar, editar e apagar
                        // ==========================
                        .requestMatchers(HttpMethod.GET, "/api/specialties/**")
                        .hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/specialties/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/specialties/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/specialties/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // CONSULTAS
                        // USER e ADMIN podem consultar
                        // Apenas ADMIN pode criar e alterar estado
                        // ==========================
                        .requestMatchers(HttpMethod.GET, "/api/appointments/**")
                        .hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/appointments/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PATCH, "/api/appointments/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/appointments/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/appointments/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // HISTÓRICO DE CONSULTAS
                        // USER e ADMIN podem consultar
                        // ==========================
                        .requestMatchers(HttpMethod.GET, "/api/appointment-history/**")
                        .hasAnyRole("USER", "ADMIN")

                        // ==========================
                        // TRIAGEM
                        // Apenas ADMIN
                        // ==========================
                        .requestMatchers("/api/triages/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/vital-signs/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // QUALQUER OUTRA ROTA
                        // Bloqueada por segurança
                        // ==========================
                        .anyRequest().denyAll()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}