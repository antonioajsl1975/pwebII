package br.edu.ifto.aula09.controller;

import br.edu.ifto.aula09.model.entity.Endereco;
import br.edu.ifto.aula09.model.entity.TipoEndereco;
import br.edu.ifto.aula09.model.repository.EnderecoRepository;
import br.edu.ifto.aula09.model.repository.TipoEnderecoRepository;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private TipoEnderecoRepository tipoEnderecoRepository;

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("endereco", new Endereco());
        model.addAttribute("tipos", tipoEnderecoRepository.findAll());
        return "endereco/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Endereco endereco, BindingResult result,
                         @RequestParam Long tipoEnderecoId, HttpSession session) {
        if (result.hasErrors()) {
            return "endereco/form";
        }

        TipoEndereco tipoEndereco = tipoEnderecoRepository.findById(tipoEnderecoId)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de Endereço não encontrado."));

        endereco.setTipoEndereco(tipoEndereco);

        // Salvando o endereço na sessão para exibir no carrinho
        session.setAttribute("enderecoEntrega", endereco);

        return "redirect:/venda/carrinho";
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
