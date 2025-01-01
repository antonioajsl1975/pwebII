package br.edu.ifto.aula09.controller;

import br.edu.ifto.aula09.model.entity.Pessoa;
import br.edu.ifto.aula09.model.entity.PessoaFisica;
import br.edu.ifto.aula09.model.repository.PessoaFisicaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("pessoafisica")
public class PessoaFisicaController {

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @GetMapping("/list")
    public String listar(
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            Model model) {
        List<Pessoa> pessoasfisicas = pessoaFisicaRepository.findAllSorted(sort, direction);
        model.addAttribute("pessoafisica", pessoasfisicas);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        return "pessoafisica/list";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(id);
            model.addAttribute("pessoafisica", pessoaFisica);
        } else {
            model.addAttribute("pessoafisica", new PessoaFisica());
        }
        return "pessoafisica/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("pessoafisica") PessoaFisica pessoaFisica, BindingResult bindigResult, RedirectAttributes attributes) {
        try {
            if (bindigResult.hasErrors()) {
                return "pessoafisica/form";
            }

            pessoaFisicaRepository.save(pessoaFisica);
            attributes.addFlashAttribute("successMessage", "Cadastrado com sucesso!");
            return "redirect:/pessoafisica/form";
        } catch (DataIntegrityViolationException e){
            bindigResult.rejectValue("cpf", "error.pessoafisica", "CPF já cadastrado");
            return "pessoafisica/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(id);
        model.addAttribute("pessoafisica", pessoaFisica);
        return "pessoafisica/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(id);
        try {
            pessoaFisicaRepository.deleteById(id);
            attributes.addFlashAttribute("successMessage", "Deletado com sucesso!");

        } catch (DataIntegrityViolationException e) {
            attributes.addFlashAttribute("errorMessage", "Não é possível excluir: existem vendas associadas a esta pessoa.");
        }
        return "redirect:/pessoafisica/list";
    }
}
