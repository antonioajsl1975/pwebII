package br.edu.ifto.aula09.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class AuthenticationConfig {

    private final UsuarioDetailsManager usuarioDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationConfig(UsuarioDetailsManager usuarioDetailsManager, PasswordEncoder passwordEncoder) {
        this.usuarioDetailsManager = usuarioDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioDetailsManager);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(List.of(authProvider));
    }
}
