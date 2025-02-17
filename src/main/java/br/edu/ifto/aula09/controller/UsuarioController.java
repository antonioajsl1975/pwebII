package br.edu.ifto.aula09.controller;

import br.edu.ifto.aula09.model.entity.Role;
import br.edu.ifto.aula09.model.entity.Usuario;
import br.edu.ifto.aula09.model.repository.RoleRepository;
import br.edu.ifto.aula09.model.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/list")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuario/list";
    }

    @GetMapping("/form")
    public String exibirFormularioCadastro(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            Usuario usuario = usuarioRepository.findById(id).orElse(new Usuario());
            model.addAttribute("usuario", usuario);
        } else {
            model.addAttribute("usuario", new Usuario());
        }
        model.addAttribute("roles", roleRepository.findAll()); // Passando as roles disponíveis
        return "usuario/form";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario) {
        // Verificando se é um novo usuário ou edição
        Usuario usuarioExistente = usuarioRepository.findByUsername(usuario.getUsername()).orElse(null);

        if (usuarioExistente != null) {
            usuario.setId(usuarioExistente.getId());
            usuario.setPassword(usuarioExistente.getPassword()); // Mantém a senha criptografada
        } else {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Criptografando senha
        }

        // Roles recuperadas do banco
        Set<Role> rolesSelecionadas = new HashSet<>();
        for (Role role : usuario.getRoles()) {
            roleRepository.findById(role.getId()).ifPresent(rolesSelecionadas::add);
        }
        usuario.setRoles(rolesSelecionadas);

        usuarioRepository.save(usuario);
        return "redirect:/usuario/list";
    }

    @GetMapping("/remover")
    public String removerUsuario(@RequestParam Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/usuario/list";
    }
}
