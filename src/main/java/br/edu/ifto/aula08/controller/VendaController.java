package br.edu.ifto.aula08.controller;

import br.edu.ifto.aula08.model.entity.PessoaFisica;
import br.edu.ifto.aula08.model.entity.PessoaJuridica;
import br.edu.ifto.aula08.model.entity.Venda;
import br.edu.ifto.aula08.model.repository.PessoaFisicaRepository;
import br.edu.ifto.aula08.model.repository.PessoaJuridicaRepository;
import br.edu.ifto.aula08.model.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Controller
@RequestMapping("venda")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;
    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @GetMapping("/form")
    public String form(Venda venda, ModelMap model) {
        model.addAttribute("venda", venda);
        model.addAttribute("clientespf", pessoaFisicaRepository.findAll());
        model.addAttribute("clientespj", pessoaJuridicaRepository.findAll());
        return "venda/form";
    }

    @GetMapping("/list")
    public ModelAndView listar(@RequestParam(required = false) String dataInicio,
                               @RequestParam(required = false) String dataFim,
                               @RequestParam(required = false) Long clienteId,
                               ModelMap model) {
        String errorMessage = null;
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if (dataInicio != null && !dataInicio.isEmpty()) {
            startDate = LocalDateTime.parse(dataInicio + "T00:00:00");
        }
        if (dataFim != null && !dataFim.isEmpty()) {
            endDate = LocalDateTime.parse(dataFim + "T23:59:59");
        }

        List<Venda> vendas = vendaRepository.findAll(startDate, endDate, clienteId);

        if (vendas.isEmpty()) {
            errorMessage = "Não há vendas este cliente no período pesquisado";
        } else {

            for (Venda venda : vendas) {
                if (venda.getPessoa() instanceof PessoaFisica) {
                    venda.setTipoPessoa("Pessoafisica");
                } else if (venda.getPessoa() instanceof PessoaJuridica) {
                    venda.setTipoPessoa("Pessoajuridica");
                }
            }
        }

        model.addAttribute("vendas", vendas);
        model.addAttribute("errorMessage", errorMessage); // Adiciona a mensagem de erro à model

        List<PessoaFisica> clientepf = pessoaFisicaRepository.findAll();
        List<PessoaJuridica> clientespj = pessoaJuridicaRepository.findAll();
        model.addAttribute("clientes", Stream.concat(clientepf.stream(), clientespj.stream()).collect(Collectors.toList()));
        return new ModelAndView("venda/list", model);
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute Venda venda) {
        if ("pessoafisica".equals(venda.getTipoPessoa())) {
            venda.setPessoa(pessoaFisicaRepository.findById(venda.getPessoa().getId()));
        } else if ("pessoajuridica".equals(venda.getPessoa())) {
            venda.setPessoa(pessoaJuridicaRepository.findById(venda.getPessoa().getId()));
        }
        vendaRepository.save(venda);
        return new ModelAndView("redirect:/venda/list");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        vendaRepository.remove(id);
        return new ModelAndView("redirect:/venda/list");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("venda", vendaRepository.venda(id));
        return new ModelAndView("/venda/form", model);
    }

    @PostMapping("/update")
    public ModelAndView update(Venda venda) {
        vendaRepository.update(venda);
        return new ModelAndView("redirect:/venda/list");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detalhe(@PathVariable Long id, ModelMap model){
        Venda venda = vendaRepository.venda(id);
        model.addAttribute("detail", venda);
        return new ModelAndView("/venda/detail", model);
    }
}