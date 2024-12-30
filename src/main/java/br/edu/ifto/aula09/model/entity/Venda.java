package br.edu.ifto.aula09.model.entity;

import br.edu.ifto.aula09.model.utils.Constraint;
import jakarta.persistence.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Component
@Scope("session")
@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataVenda;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<ItemVenda> itensVenda = new ArrayList<>();

    @ManyToOne
    @JoinColumn(
            name = "pessoa_id", nullable = false,
            foreignKey = @ForeignKey(name = Constraint.fk_venda__pessoa)
    )
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Double totalVenda() {
        return itensVenda.stream().mapToDouble(item -> item.totalItem().doubleValue()).sum();

//        double total = 0.0;
//        for (ItemVenda itemVenda : itensVenda) {
//            total += itemVenda.totalItem().doubleValue();
//        }
//        return total;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }
}
