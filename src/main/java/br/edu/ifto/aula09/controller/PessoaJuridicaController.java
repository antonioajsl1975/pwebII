package br.edu.ifto.aula09.controller;

import br.edu.ifto.aula09.model.entity.PessoaJuridica;
import br.edu.ifto.aula09.model.repository.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("pessoajuridica")
public class PessoaJuridicaController {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("pessoasjuridicas", pessoaJuridicaRepository.findAll());
        return new ModelAndView("pessoajuridica/list");
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
    public String save(PessoaJuridica pessoaJuridica) {
        pessoaJuridicaRepository.save(pessoaJuridica);
        return "redirect:/pessoajuridica/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(id);
        model.addAttribute("pessoajuridica", pessoaJuridica);
        return "pessoajuridica/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(id);
        if (pessoaJuridica != null) {
            pessoaJuridicaRepository.deleteById(id);
        }
        return "redirect:/pessoajuridica/list";
    }
}
