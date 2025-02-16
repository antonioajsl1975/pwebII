package br.edu.ifto.aula09.model.entity;

import br.edu.ifto.aula09.model.utils.ValidTelefone;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Scope("session")
//@Component
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Email
    @NotBlank
    private String email;

    @ValidTelefone
    @NotBlank
    private String telefone;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL, optional = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "pessoa", orphanRemoval = false)
    private List<Venda> vendas = new ArrayList<>();

    public abstract String getNomeOuRazaoSocial();

    public abstract String getCpfOuCnpj();

    public String getTelefoneFormatado() {
        return telefone.replaceAll("(\\d{2})(\\d{4,5})(\\d{4})", "($1) $2-$3");
    }
}