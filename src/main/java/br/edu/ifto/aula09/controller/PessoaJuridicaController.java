package br.edu.ifto.aula09.controller;

import br.edu.ifto.aula09.model.entity.Pessoa;
import br.edu.ifto.aula09.model.entity.PessoaFisica;
import br.edu.ifto.aula09.model.entity.PessoaJuridica;
import br.edu.ifto.aula09.model.repository.PessoaJuridicaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("pessoajuridica")
public class PessoaJuridicaController {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @GetMapping("/list")
    public String listar(
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            Model model) {
        List<Pessoa> pessoasjuridicas = pessoaJuridicaRepository.findAllSorted(sort, direction);
        model.addAttribute("pessoasjuridicas", pessoasjuridicas);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        return "pessoajuridica/list";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(id);
            model.addAttribute("pessoajuridica", pessoaJuridica);
        } else {
            model.addAttribute("pessoajuridica", new PessoaJuridica());
        }
        return "pessoajuridica/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("pessoajuridica") PessoaJuridica pessoaJuridica, BindingResult bindigResult, RedirectAttributes attributes) {
        try {
            if (bindigResult.hasErrors()) {
                return "pessoajuridica/form";
            }

            pessoaJuridicaRepository.save(pessoaJuridica);
            attributes.addFlashAttribute("successMessage", "Cadastrado com sucesso!");
            return "redirect:/pessoajuridica/form";
        } catch (DataIntegrityViolationException e){
            bindigResult.rejectValue("cnpj", "error.pessoajuridica", "CNPJ já cadastrado");
            return "pessoajuridica/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(id);
        model.addAttribute("pessoajuridica", pessoaJuridica);
        return "pessoajuridica/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(id);
        try {
            pessoaJuridicaRepository.deleteById(id);
            attributes.addFlashAttribute("successMessage", "Deletado com sucesso!");

        } catch (DataIntegrityViolationException e) {
            attributes.addFlashAttribute("errorMessage", "Não é possível excluir: existem vendas associadas a esta pessoa.");
        }
        return "redirect:/pessoajuridica/list";
    }
}
