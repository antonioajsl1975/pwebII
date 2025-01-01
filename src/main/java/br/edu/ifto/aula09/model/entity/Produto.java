package br.edu.ifto.aula09.model.entity;

import br.edu.ifto.aula09.model.utils.Constraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = Constraint.uc_produto__descricao, columnNames = "descricao")})
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100, message = "Tamanho deve estar entre 3 e 100 caracteres.")
    private String descricao;

    @NotNull
    @DecimalMin(value = "0.01", message = "Deve ser maior que zero.")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal valor;

    public Produto() {}

    public Produto(Long id, String descricao, BigDecimal valor) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
