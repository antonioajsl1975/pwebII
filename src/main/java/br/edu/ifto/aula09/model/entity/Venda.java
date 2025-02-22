package br.edu.ifto.aula09.model.entity;

import br.edu.ifto.aula09.model.utils.Constraint;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Component
@Scope("session")
@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
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

    @ManyToOne
    @JoinColumn(name = "endereco_entrega_id")
    private Endereco enderecoEntrega;

    public Double totalVenda() {
        return itensVenda.stream().mapToDouble(item -> item.totalItem().doubleValue()).sum();
    }

}
