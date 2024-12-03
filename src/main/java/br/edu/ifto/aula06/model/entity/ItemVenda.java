package br.edu.ifto.aula06.model.entity;

import br.edu.ifto.aula06.model.utils.Constraint;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double quantidade;

    @JoinColumn(foreignKey = @ForeignKey(name = Constraint.fk_item__produto))
    @ManyToOne
    private Produto produto;

    @JoinColumn(foreignKey = @ForeignKey(name = Constraint.fk_item__venda))
    @ManyToOne
    private Venda venda;

    public ItemVenda() {}

    public ItemVenda(Double quantidade, Produto produto, Venda venda) {
        this.quantidade = quantidade;
        this.produto = produto;
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

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public BigDecimal totalItem() {
        return produto.getValor().multiply(BigDecimal.valueOf(quantidade));
    }
}
