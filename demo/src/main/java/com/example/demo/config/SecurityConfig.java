package com.example.demo.config;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.demo.modelo.Rol;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // estáticos
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // público
                .requestMatchers("/", "/saluda").permitAll()
                // Albums/Generos: USUARIO o ADMIN
                .requestMatchers("/albums/**", "/generos/**").hasAnyRole(Rol.USUARIO.toString(), Rol.ADMIN.toString(), Rol.MANAGER.toString())
                // el resto: autenticado
                .anyRequest().authenticated()
        );

        // login form
        http.formLogin(form -> form
                .defaultSuccessUrl("/albums", true)
                .permitAll()
        );

        // logout
        http.logout(logout -> logout.permitAll());

        return http.build();
    }
}
