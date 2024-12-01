package br.edu.ifto.aula07.controller;

import br.edu.ifto.aula07.model.entity.PessoaFisica;
import br.edu.ifto.aula07.model.repository.PessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("pessoafisica")
public class PessoaFisicaController {

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("pessoasfisicas", pessoaFisicaRepository.findAll());
        return new ModelAndView("pessoafisica/list");
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
    public String save(PessoaFisica pessoaFisica) {
        pessoaFisicaRepository.save(pessoaFisica);
        return "redirect:/pessoafisica/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(id);
        model.addAttribute("pessoafisica", pessoaFisica);
        return "pessoafisica/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(id);
        if (pessoaFisica != null) {
            pessoaFisicaRepository.deleteById(id);
        }
        return "redirect:/pessoafisica/list";
    }
}
