package br.edu.ifto.aula09.controller;

import br.edu.ifto.aula09.model.entity.Endereco;
import br.edu.ifto.aula09.model.repository.EnderecoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
@RequestMapping("endereco")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public EnderecoController(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("enderecos", new Endereco());
        return "endereco/form";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@Valid Endereco endereco, BindingResult result) {
        if(result.hasErrors()) {
            return "endereco/form";
        }
        enderecoRepository.save(endereco);
        return "redirect:/endereco/list";
    }

    @GetMapping("/list")
    public String listar(Model model) {
        model.addAttribute("enderecos", enderecoRepository.findAll());
        return "endereco/list";
    }


    @GetMapping("/buscar-cep")
    @ResponseBody
    public Map<String, String> buscarCep(@RequestParam String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> endereco = restTemplate.getForObject(url, Map.class);
        return endereco;
    }
}
