package br.edu.ifto.aula07.controller;

import br.edu.ifto.aula07.model.entity.ItemVenda;
import br.edu.ifto.aula07.model.repository.ItemVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("itemvenda")
public class ItemVendaController {

    @Autowired
    ItemVendaRepository itemVendaRepository;

    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("itensVenda", itemVendaRepository.itensVenda());
        return new ModelAndView("/itemvenda/list");
    }

    @GetMapping("/form")
    public String form(ItemVenda itemVenda) {
        return "itemvenda/form";
    }

    @PostMapping("/save")
    public ModelAndView save(ItemVenda itemVenda) {
        itemVendaRepository.save(itemVenda);
        return new ModelAndView("redirect:/itemvenda/list");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        itemVendaRepository.remove(id);
        return new ModelAndView("redirect:/itemvenda/list");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("itemVenda", itemVendaRepository.itemVenda(id));
        return new ModelAndView("/itemvenda/form", model);
    }

    @PostMapping("/update")
    public ModelAndView update(ItemVenda itemVenda) {
        itemVendaRepository.save(itemVenda);
        return new ModelAndView("redirect:/itemvenda/list");
    }
}
