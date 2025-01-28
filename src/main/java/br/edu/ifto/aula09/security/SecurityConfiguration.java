package br.edu.ifto.aula09.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        customizer ->
                                customizer
                                        .requestMatchers("/pessoafisica/form").permitAll()
                                        .requestMatchers("/pessoafisica/list").hasAnyRole("ADMIN")
                                        .requestMatchers(HttpMethod.POST, "/pessoafisica/save").permitAll()
                                        .anyRequest()
                                        .authenticated()
                )
                .formLogin(customizer ->
                        customizer
                                .loginPage("/login") // Adicione a barra inicial
                                .defaultSuccessUrl("/pessoafisica/form", true)
                                .permitAll()
                )
                .httpBasic(withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL para acionar o logout
                        .logoutSuccessUrl("/login?logout") // URL para redirecionar após o logout
                        .invalidateHttpSession(true) // Invalida a sessão
                        .deleteCookies("JSESSIONID") // Remove cookies
                        .permitAll()
                )
                .rememberMe(withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withUsername("user")
                .password(passwordEncoder().encode("123"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}