package br.edu.ifto.aula09.model.entity;

import br.edu.ifto.aula09.model.utils.ValidTelefone;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany
    @JoinTable(name = "pessoa_endereco",
            joinColumns = @JoinColumn(name = "pessoa_id"),
            inverseJoinColumns = @JoinColumn(name = "endereco_id"))
    private List<Endereco> enderecos = new ArrayList<>();

    public abstract String getNomeOuRazaoSocial();

    public abstract String getCpfOuCnpj();

    public String getTelefoneFormatado() {
        return telefone.replaceAll("(\\d{2})(\\d{4,5})(\\d{4})", "($1) $2-$3");
    }
}