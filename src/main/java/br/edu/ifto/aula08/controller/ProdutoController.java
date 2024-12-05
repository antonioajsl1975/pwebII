package br.edu.ifto.aula08.controller;

import br.edu.ifto.aula08.model.entity.Produto;
import br.edu.ifto.aula08.model.repository.ItemVendaRepository;
import br.edu.ifto.aula08.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @GetMapping("/list")
    public ModelAndView listar(@RequestParam(value = "descricao", required = false) String descricao, ModelMap model) {
        List<Produto> produtos;
        if (descricao != null && !descricao.isEmpty()) {
            produtos = produtoRepository.findByDescricao(descricao);
        } else {
            produtos = produtoRepository.produtos();
        }
        model.addAttribute("produtos", produtos);
        return new ModelAndView("/produto/list");
    }

    @GetMapping("/form")
    public String form(Produto produto) {
        return "produto/form";
    }

    @PostMapping("/save")
    public ModelAndView save(Produto produto) {
        produtoRepository.save(produto);
        return new ModelAndView("redirect:/produto/list");
    }

    //@PathVariable é utilizado quando o valor da variável é passada diretamente na URL
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (itemVendaRepository.existsByProdutoId(id)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Não é possível excluir o produto, pois ele está associado a uma venda.");
            return "redirect:/produto/list";
        }
        produtoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Produto excluído com sucesso!");
        return "redirect:/produto/list";
    }

    //@PathVariable é utilizado quando o valor da variável é passada diretamente na URL
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("produto", produtoRepository.produto(id));
        return new ModelAndView("/produto/form", model);
    }

    @PostMapping("/update")
    public ModelAndView update(Produto produto) {
        produtoRepository.save(produto);
        return new ModelAndView("redirect:/produto/list");
    }
}
