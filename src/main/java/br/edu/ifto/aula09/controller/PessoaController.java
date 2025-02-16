package br.edu.ifto.aula09.controller;

import br.edu.ifto.aula09.model.entity.Pessoa;
import br.edu.ifto.aula09.model.entity.PessoaFisica;
import br.edu.ifto.aula09.model.entity.PessoaJuridica;
import br.edu.ifto.aula09.model.repository.PessoaFisicaRepository;
import br.edu.ifto.aula09.model.repository.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

//@Scope("request")
@Controller
@RequestMapping("pessoa")
public class PessoaController {

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

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