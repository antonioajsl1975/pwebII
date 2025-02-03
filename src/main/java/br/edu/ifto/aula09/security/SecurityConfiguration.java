package br.edu.ifto.aula09.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/produto/catalogo", "/venda/carrinho").permitAll()
                        .requestMatchers("/venda/adicionaCarrinho/**", "/venda/alterarQuantidade/**", "/venda/removerProdutoCarrinho/**").permitAll()
                        .requestMatchers("/departamento/form", "/departamento/list",
                                "/produto/list", "/produto/form",
                                "/pessoafisica/list", "/pessoajuridica/list",
                                "/venda/list").hasRole("ADMIN") // Apenas ADMIN pode acessar
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/produto/catalogo", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN") // Spring Security já adiciona "ROLE_" automaticamente
                .build();

        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("user123"))
                .roles("USER") // Será convertido para "ROLE_USER"
                .build();

        UserDetails cliente = User.withUsername("cliente")
                .password(passwordEncoder().encode("cliente123"))
                .roles("CLIENTE") // Será convertido para "ROLE_CLIENTE"
                .build();

        return new InMemoryUserDetailsManager(admin, user, cliente);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}