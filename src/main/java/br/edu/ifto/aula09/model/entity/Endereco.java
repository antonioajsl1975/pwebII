package br.edu.ifto.aula09.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "CEP é obrigatório")
    private String cep;

    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;

    private String complemento;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    @Pattern(regexp = "[A-Z]{2}", message = "Estado inválido")
    private String estado;

    @NotBlank(message = "Número é obrigatório")
    private String numero;

    @ManyToMany
    @JoinTable(name = "endereco_pessoa",
            joinColumns = @JoinColumn(name = "endereco_id"),
            inverseJoinColumns = @JoinColumn(name = "pessoa_id"))
    private List<Pessoa> pessoas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "tipo_endereco_id", nullable = false)
    private TipoEndereco tipoEndereco;
}

