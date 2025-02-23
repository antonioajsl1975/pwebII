package br.edu.ifto.aula09.security;

import br.edu.ifto.aula09.model.entity.Usuario;
import br.edu.ifto.aula09.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class UsuarioDetailsManager implements UserDetailsManager {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> listAllUsers() {
        return usuarioRepository.findAll();
    }

    @Autowired
    public UsuarioDetailsManager(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> {
                    SecurityContextHolder.clearContext();
                    return new RuntimeException("Usuário não encontrado!");
                });
    }

    @Override
    public void createUser(UserDetails user) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername(user.getUsername());
        novoUsuario.setPassword(passwordEncoder.encode(user.getPassword()));
        usuarioRepository.save(novoUsuario);
    }

    @Override
    public void updateUser(UserDetails user) {
        Usuario usuario = usuarioRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        usuario.setPassword(passwordEncoder.encode(user.getPassword()));
        usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUser(String username) {
        usuarioRepository.findByUsername(username).ifPresent(usuarioRepository::delete);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return usuarioRepository.findByUsername(username).isPresent();
    }
}
