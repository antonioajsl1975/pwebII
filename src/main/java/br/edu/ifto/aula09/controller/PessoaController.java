package br.edu.ifto.aula09.controller;

import br.edu.ifto.aula09.model.entity.*;
import br.edu.ifto.aula09.model.repository.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//@Scope("request")
@Controller
@RequestMapping("pessoa")
public class PessoaController {

    private final PessoaRepository pessoaRepository;
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    public PessoaController(PessoaRepository pessoaRepository, UsuarioRepository usuarioRepository,
                            RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.pessoaRepository = pessoaRepository;
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/form")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "pessoa/form";
    }

    @Transactional
    @PostMapping("/cadastro")
    public String cadastrarPessoaEUsuario(@RequestParam String tipoPessoa,
                                          @RequestParam String nomeOuRazaoSocial,
                                          @RequestParam String cpfOuCnpj,
                                          @RequestParam String email,
                                          @RequestParam String telefone,
                                          @ModelAttribute @Valid Usuario usuario,
                                          Model model,
                                          RedirectAttributes attributes) {

        Pessoa pessoa;
        if ("fisica".equals(tipoPessoa)) {
            PessoaFisica pessoaFisica = new PessoaFisica();
            pessoaFisica.setNome(nomeOuRazaoSocial);
            pessoaFisica.setCpf(cpfOuCnpj);
            pessoaFisica.setEmail(email);
            pessoaFisica.setTelefone(telefone);
            pessoa = pessoaFisica;
        } else {
            PessoaJuridica pessoaJuridica = new PessoaJuridica();
            pessoaJuridica.setRazaoSocial(nomeOuRazaoSocial);
            pessoaJuridica.setCnpj(cpfOuCnpj);
            pessoaJuridica.setEmail(email);
            pessoaJuridica.setTelefone(telefone);
            pessoa = pessoaJuridica;
        }

        pessoaRepository.save(pessoa);

        usuario.setPessoa(pessoa);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Optional<Role> roleUser = Optional.ofNullable(roleRepository.findByNome("ROLE_USER"));
        roleUser.ifPresentOrElse(
                r -> usuario.setRoles(Set.of(r)),
                () -> {
                    throw new RuntimeException("Role ROLE_USER não encontrada no banco de dados.");
                }
        );

        usuarioRepository.save(usuario);

        attributes.addFlashAttribute("successMessage", "Cadastro realizado com sucesso!");
        return "redirect:/produto/catalogo";
    }


    @GetMapping("/list")
    public ModelAndView listar(@RequestParam(value = "nomeRazaoSocial", required = false) String nomeRazaoSocial, ModelMap model) {
        List<PessoaFisica> pessoasFisicas;
        List<PessoaJuridica> pessoasJuridicas;

        if (nomeRazaoSocial != null && !nomeRazaoSocial.isEmpty()) {
            pessoasFisicas = pessoaFisicaRepository.findByNome(nomeRazaoSocial);
            pessoasJuridicas = pessoaJuridicaRepository.findByRazaoSocial(nomeRazaoSocial);
        } else {
            pessoasFisicas = pessoaFisicaRepository.findAll();
            pessoasJuridicas = pessoaJuridicaRepository.findAll();
        }

        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.addAll(pessoasFisicas);
        pessoas.addAll(pessoasJuridicas);

        model.addAttribute("pessoas", pessoas);
        return new ModelAndView("pessoa/list");
    }
}