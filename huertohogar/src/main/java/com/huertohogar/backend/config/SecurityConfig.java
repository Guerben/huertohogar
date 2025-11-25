package com.huertohogar.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .csrf(csrf -> csrf.disable())

                // Configurar qué endpoints son públicos y cuáles protegidos
                .authorizeHttpRequests(auth -> auth
                        // Rutas PÚBLICAS
                        .requestMatchers("/api/auth/**").permitAll()
                        // permitir sólo GET para categorías y productos públicamente
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()
                        // OpenAPI / Swagger UI
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // Todas las demás rutas requieren autenticación
                        .anyRequest().authenticated())

                // Configurar sesiones: permitir sesiones cuando se usen métodos de login
                // basados en sesión
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        // Habilitar OAuth2 login opcional (social login)
        // NOTA: Descomentar cuando tengas configuradas credenciales OAuth2 en
        // application.properties
        // http.oauth2Login(oauth2 -> oauth2
        // .defaultSuccessUrl("/api/auth/oauth2/success", true));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
