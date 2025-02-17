package br.edu.ifto.aula09.security;

import br.edu.ifto.aula09.model.entity.Role;
import br.edu.ifto.aula09.model.entity.Usuario;
import br.edu.ifto.aula09.model.repository.RoleRepository;
import br.edu.ifto.aula09.model.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DatabaseSeeder {

//    @Bean
//    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
//        return args -> {
//            if (usuarioRepository.count() == 1) {
//                // Criando role ADMIN
//                Role roleUser = roleRepository.findByNome(("ROLE_ADMIN"));
//                if (roleUser == null) {
//                    roleUser = new Role();
//                    roleUser.setNome("ROLE_ADMIN");
//                    roleRepository.save(roleUser);
//                }
//
//                // Criando usuário ADMIN
//                Usuario user = new Usuario();
//                user.setUsername("admin");
//                user.setPassword(passwordEncoder.encode("123"));
//
//                Set<Role> roles = new HashSet<>();
//                roles.add(roleUser);
//                user.setRoles(roles);
//
//                usuarioRepository.save(user);
//
//                System.out.println("Usuário 'admin' criado com senha '123456'");
//            }
//        };
//    }
}

