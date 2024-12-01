package br.edu.ifto.aula06.controller;

import br.edu.ifto.aula06.model.entity.Produto;
import br.edu.ifto.aula06.model.repository.ItemVendaRepository;
import br.edu.ifto.aula06.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("produto")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ItemVendaRepository itemVendaRepository;

    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("produtos", produtoRepository.produtos());
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
