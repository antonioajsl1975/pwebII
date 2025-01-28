package br.edu.ifto.aula09.model.entity;

import br.edu.ifto.aula09.model.utils.Constraint;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double quantidade;
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = Constraint.fk_item__produto))
    private Produto produto;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = Constraint.fk_item__venda))
    private Venda venda;

    public ItemVenda() {}

    public ItemVenda(Double quantidade, Produto produto, Venda venda) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.preco = produto.getValor();
        this.venda = venda;
    }

    public ItemVenda(Double quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.preco = produto.getValor();
        this.venda = venda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public BigDecimal totalItem() {
        return preco.multiply(BigDecimal.valueOf(quantidade));
    }
}
