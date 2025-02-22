package br.edu.ifto.aula09.controller;

import br.edu.ifto.aula09.exception.UsuarioNaoAutenticadoException;
import br.edu.ifto.aula09.model.entity.*;
import br.edu.ifto.aula09.model.repository.*;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    @Autowired
    UsuarioRepository usuarioRepository;

    public String errorMessage = null;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private TipoEnderecoRepository tipoEnderecoRepository;

    @ModelAttribute("venda")
    public Venda initVenda() {
        return new Venda();
    }

    @GetMapping("/carrinho")
    public String chamarCarrinho(Model model, HttpSession session) {

        if (session.getAttribute("venda") == null) {
            session.setAttribute("venda", this.venda);
        }
        model.addAttribute("venda", this.venda);
        model.addAttribute("enderecoEntrega", session.getAttribute("enderecoEntrega"));
        model.addAttribute("tipos", tipoEnderecoRepository.findAll());
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

    @PostMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        Pessoa pessoa = obterPessoaLogada();
        model.addAttribute("pessoa", pessoa);

        List<Endereco> enderecos = enderecoRepository.findAll();
        model.addAttribute("enderecos", enderecos);

        Double totalVenda = this.venda.totalVenda();

        session.setAttribute("valorTotal", totalVenda);

        model.addAttribute("total", totalVenda);

        model.addAttribute("venda", this.venda);
        model.addAttribute("tipos", tipoEnderecoRepository.findAll());
        model.addAttribute("endereco", new Endereco());

        return "venda/checkout";
    }


    private Pessoa obterPessoaLogada() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new IllegalStateException("Usuário não autenticado.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        var usuarioLogado = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado no banco de dados."));

        Pessoa pessoa = usuarioLogado.getPessoa();
        if (pessoa == null) {
            throw new IllegalStateException("Usuário não possui uma pessoa associada.");
        }

        return pessoa;
    }

    @PostMapping("/finalizar")
    public String finalizarVenda(@RequestParam Map<String, String> formData, HttpSession session, Model model) {
        Pessoa pessoa = obterPessoaLogada();

        Endereco enderecoEntrega;
        if (formData.containsKey("cep")) {
            enderecoEntrega = new Endereco();
            enderecoEntrega.setCep(formData.get("cep"));
            enderecoEntrega.setLogradouro(formData.get("logradouro"));
            enderecoEntrega.setNumero(formData.get("numero"));
            enderecoEntrega.setBairro(formData.get("bairro"));
            enderecoEntrega.setCidade(formData.get("cidade"));
            enderecoEntrega.setEstado(formData.get("estado"));
            enderecoEntrega.setPessoas(List.of(pessoa));
            enderecoRepository.save(enderecoEntrega);
        } else {
           //analisar se isso aqui é útil
            enderecoEntrega = pessoa.getEnderecos().isEmpty() ? null : pessoa.getEnderecos().get(0);
        }

        if (enderecoEntrega == null) {
            model.addAttribute("errorMessage", "Por favor, informe um endereço de entrega.");
            return "redirect:/venda/carrinho";
        }

        this.venda.setPessoa(pessoa);
        this.venda.setEnderecoEntrega(enderecoEntrega);
        this.venda.setDataVenda(LocalDateTime.now());

        vendaRepository.save(this.venda);

        session.removeAttribute("enderecoEntrega");
        session.removeAttribute("venda");
        this.venda = new Venda();

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

        }

        model.addAttribute("vendas", vendas);
        model.addAttribute("errorMessage", errorMessage); // Adiciona a mensagem de erro à model

        List<PessoaFisica> clientepf = pessoaFisicaRepository.findAll();
        List<PessoaJuridica> clientespj = pessoaJuridicaRepository.findAll();
        model.addAttribute("clientes", Stream.concat(clientepf.stream(), clientespj.stream()).collect(Collectors.toList()));
        return new ModelAndView("venda/list", model);
    }

    @GetMapping("/minhas-vendas")
    public ModelAndView listarPorUsuario(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return new ModelAndView("redirect:/login");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario usuarioLogado = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado no banco de dados."));

        Pessoa pessoa = usuarioLogado.getPessoa();
        if (pessoa == null) {
            model.addAttribute("errorMessage", "Usuário não possui uma pessoa associada.");
            return new ModelAndView("venda/minhas-vendas", model);
        }

        List<Venda> vendas = vendaRepository.findByPessoa(pessoa);

        model.addAttribute("vendas", vendas);
        return new ModelAndView("venda/minhas-vendas", model);
    }

    @GetMapping("/detail-minhas-vendas/{id}")
    public ModelAndView detalheMinhasVendas(@PathVariable Long id, ModelMap model) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return new ModelAndView("redirect:/login");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario usuarioLogado = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado no banco de dados."));

        Pessoa pessoa = usuarioLogado.getPessoa();
        if (pessoa == null) {
            model.addAttribute("errorMessage", "Usuário não possui uma pessoa associada.");
            return new ModelAndView("redirect:/venda/minhas-vendas");
        }

        Venda venda = vendaRepository.venda(id);
        if (venda == null || !venda.getPessoa().equals(pessoa)) {
            model.addAttribute("errorMessage", "Acesso negado: Você não pode visualizar esta venda.");
            return new ModelAndView("redirect:/venda/minhas-vendas");
        }

        model.addAttribute("detail", venda);
        return new ModelAndView("venda/detail-minhas-vendas", model);
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