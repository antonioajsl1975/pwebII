package br.edu.ifto.aula09.security;

import br.edu.ifto.aula09.model.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UsuarioRepository usuarioRepository;

    public SecurityConfiguration(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/logout"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/produto/catalogo", "/venda/carrinho",
                                "/venda/adicionaCarrinho/*", "/venda/removerProdutoCarrinho/*",
                                "/venda/alterarQuantidade/*", "/pessoa/form",
                                "/pessoa/cadastro", "/venda/alterarQuantidade/**", "/venda/finalizar").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/produto/catalogo?logout=true")
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe.userDetailsService(userDetailsManager()));

        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        return new UsuarioDetailsManager(usuarioRepository, passwordEncoder());
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
