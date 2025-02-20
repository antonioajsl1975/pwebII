package br.edu.ifto.aula09.controller;

import br.edu.ifto.aula09.model.entity.Pessoa;
import br.edu.ifto.aula09.model.entity.PessoaFisica;
import br.edu.ifto.aula09.model.entity.Produto;
import br.edu.ifto.aula09.model.repository.ItemVendaRepository;
import br.edu.ifto.aula09.model.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("produto")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ItemVendaRepository itemVendaRepository;

    @GetMapping("/listSorted")
    public String listSorted(
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            Model model) {
        List<Produto> produtos = produtoRepository.findAllSorted(sort, direction);
        model.addAttribute("produtos", produtos);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        return "produto/list";
    }

    @GetMapping("/list")
    public ModelAndView listar(@RequestParam(value = "descricao", required = false) String descricao, ModelMap model) {
        List<Produto> produtos;
        if (descricao != null && !descricao.isEmpty()) {
            produtos = produtoRepository.findByDescricao(descricao);
        } else {
            produtos = produtoRepository.findAll();
        }
        model.addAttribute("produtos", produtos);
        return new ModelAndView("/produto/list");
    }

    @GetMapping("/catalogo")
    public ModelAndView catalogo(@RequestParam(value = "descricao", required = false) String descricao,
                                 @RequestParam(value = "logout", required = false) String logout,
                                 ModelMap model) {
        List<Produto> catalogo;
        if (descricao != null && !descricao.isEmpty()) {
            catalogo = produtoRepository.findByDescricao(descricao);
        } else {
            catalogo = produtoRepository.findAll();
        }
        model.addAttribute("catalogo", catalogo);
        if ("true".equals(logout)) {
            model.addAttribute("errorMessage", "Você saiu com sucesso!");
        }
        return new ModelAndView("/produto/catalogo");
    }

    @GetMapping("/form")
    public String form(Produto produto) {
        return "produto/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("produto") Produto produto, BindingResult bindigResult, RedirectAttributes attributes) {
        try {
            if (bindigResult.hasErrors()) {
                return "produto/form";
            }

            produtoRepository.save(produto);
            attributes.addFlashAttribute("successMessage", "Salvo com sucesso!");
            return "redirect:/produto/form";
        } catch (DataIntegrityViolationException e){
            bindigResult.rejectValue("descricao", "error.produto", "Já existe um produto com a descrição informada.");
            return "produto/form";
        }
    }

    //@PathVariable é utilizado quando o valor da variável é passada diretamente na URL
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (itemVendaRepository.existsByProdutoId(id)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Impossível excluir. Produto está associado a uma venda.");
            return "redirect:/produto/list";
        }
        produtoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Produto excluído com sucesso!");
        return "redirect:/produto/list";
    }

    //@PathVariable é utilizado quando o valor da variável é passada diretamente na URL
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("produto", produtoRepository.findById(id));
        return new ModelAndView("/produto/form", model);
    }

//    @PostMapping("/update")
//    public ModelAndView update(Produto produto) {
//        produtoRepository.save(produto);
//        return new ModelAndView("redirect:/produto/list");
//    }
}
