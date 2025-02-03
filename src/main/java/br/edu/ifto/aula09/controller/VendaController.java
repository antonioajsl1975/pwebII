package br.edu.ifto.aula09.controller;

import br.edu.ifto.aula09.model.entity.*;
import br.edu.ifto.aula09.model.repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Controller
@Scope("request")
@RequestMapping("venda")
public class VendaController {
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    PessoaRepository pessoaRepository;
    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;
    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    Venda venda;

    public String errorMessage = null;

    @ModelAttribute("venda")
    public Venda initVenda() {
        return new Venda();
    }

    @GetMapping("/carrinho")
    public String chamarCarrinho(Model model) {
        model.addAttribute("venda", this.venda);

        List<Pessoa> clientes = new ArrayList<>();
        clientes.addAll(pessoaFisicaRepository.findAll());
        clientes.addAll(pessoaJuridicaRepository.findAll());
        model.addAttribute("clientes", clientes);
        return "venda/carrinho";
    }


    @GetMapping("/adicionaCarrinho/{id}")
    public ModelAndView adicionaCarrinho(@PathVariable Long id) {
       Produto produto = produtoRepository.findById(id);

        boolean itemExistente = false;
        for (ItemVenda itemVenda : this.venda.getItensVenda()) {
            if (itemVenda.getProduto().getId().equals(produto.getId())) {
                itemVenda.setQuantidade(itemVenda.getQuantidade() +1);
                itemExistente = true;
                break;
            }
        }
        if (!itemExistente) {

            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setProduto(produto);
            itemVenda.setPreco(produto.getValor());
            itemVenda.setQuantidade(1.0);
            itemVenda.setVenda(this.venda);
            this.venda.getItensVenda().add(itemVenda);
        }
        return new ModelAndView("redirect:/venda/carrinho");
    }

    @GetMapping("/removerProdutoCarrinho/{id}")
    public String removerProdutoCarrinho(@PathVariable Long id){
        for (ItemVenda itemVenda : this.venda.getItensVenda()) {
            if (itemVenda.getProduto().getId().equals(id)) {
                this.venda.getItensVenda().remove(itemVenda);
                break;
            }
        }
        return "redirect:/venda/carrinho";
    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public String alterarQuantidade(@PathVariable Long id,
                                          @PathVariable Integer acao){
        for (ItemVenda itemVenda : this.venda.getItensVenda()) {
            if (itemVenda.getProduto().getId().equals(id)) {
                if (acao.equals(1)) {
                    itemVenda.setQuantidade(itemVenda.getQuantidade() + 1);
                } else if (acao.equals(0) && (itemVenda.getQuantidade() > 1)){
                    itemVenda.setQuantidade(itemVenda.getQuantidade() - 1);
                }
                break;
            }
        }
        return "redirect:/venda/carrinho";
    }

    @PostMapping("/finalizar")
    public String finalizarVenda(@RequestParam(required = false) Long pessoaId, HttpSession session, Model model) {

        if (this.venda.getItensVenda().isEmpty()) {
            model.addAttribute("errorMessage", "Impossível finalizar a venda. Carrinho vazio.");
            return "venda/carrinho";
        }
        this.venda.setDataVenda(LocalDateTime.now());
        Pessoa pessoa = pessoaRepository.findById(pessoaId);

        if (pessoa != null) {
            this.venda.setPessoa(pessoa);
        } else {
            throw new IllegalArgumentException("Cliente inválido ou inexistente!");
        }

        vendaRepository.save(this.venda);
        session.removeAttribute("venda");
        model.addAttribute("successMessage", "Venda finalizada com sucesso!");
        return "redirect:/venda/carrinho";
    }

    @GetMapping("/list")
    public ModelAndView listar(@RequestParam(required = false, name = "dataInicio") String dataInicio,
                               @RequestParam(required = false, name ="dataFim") String dataFim,
                               @RequestParam(required = false, name = "clienteId") Long clienteId,
                               ModelMap model) {
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
            errorMessage = "Não há vendas no período pesquisado";
        } else {

//            for (Venda venda : vendas) {
//                if (venda.getPessoa() instanceof PessoaFisica) {
//                    venda.setTipoPessoa("Pessoafisica");
//                } else if (venda.getPessoa() instanceof PessoaJuridica) {
//                    venda.setTipoPessoa("Pessoajuridica");
//                }
//            }
        }

        model.addAttribute("vendas", vendas);
        model.addAttribute("errorMessage", errorMessage); // Adiciona a mensagem de erro à model

        List<PessoaFisica> clientepf = pessoaFisicaRepository.findAll();
        List<PessoaJuridica> clientespj = pessoaJuridicaRepository.findAll();
        model.addAttribute("clientes", Stream.concat(clientepf.stream(), clientespj.stream()).collect(Collectors.toList()));
        return new ModelAndView("venda/list", model);
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        vendaRepository.remove(id);
        return new ModelAndView("redirect:/venda/list");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detalhe(@PathVariable Long id, ModelMap model){
        Venda venda = vendaRepository.venda(id);
        model.addAttribute("detail", venda);
        return new ModelAndView("/venda/detail", model);
    }
}