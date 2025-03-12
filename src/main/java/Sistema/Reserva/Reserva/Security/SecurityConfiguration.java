package Sistema.Reserva.Reserva.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Configuration
@Tag(name = "Configurações de Segurança", description = "Controlador das configurações de segurança")
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;

    public SecurityConfiguration(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    @Operation(summary = "Configuração de segurança")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register").permitAll()

                        .requestMatchers("/station/create").hasRole("ADMINISTRADOR")
                        .requestMatchers("/station/update").hasAnyRole("ADMINISTRADOR", "FUNCIONARIO")
                        .requestMatchers("/station/delete").hasRole("ADMINISTRADOR")
                        .requestMatchers("/station/enable").hasRole("ADMINISTRADOR")

                        .requestMatchers("/room/create").hasRole("ADMINISTRADOR")
                        .requestMatchers("/room/update").hasAnyRole("ADMINISTRADOR", "FUNCIONARIO")
                        .requestMatchers("/room/delete").hasRole("ADMINISTRADOR")
                        .requestMatchers("/room/enable").hasRole("ADMINISTRADOR")

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html")
                        .permitAll()

                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(securityFilter,
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Operation(summary = "Configuração de segurança")
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    @Operation(summary = "Configuração de segurança")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
