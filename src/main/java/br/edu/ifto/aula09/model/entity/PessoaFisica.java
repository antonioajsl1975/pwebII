package br.edu.ifto.aula09.model.entity;

import br.edu.ifto.aula09.model.utils.Constraint;
import br.edu.ifto.aula09.model.utils.ValidCPF;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = Constraint.uc_pessoafisica__cpf, columnNames = "cpf")})
@Inheritance(strategy = InheritanceType.JOINED)
public class PessoaFisica extends Pessoa implements Serializable {

    @NotBlank
    @ValidCPF
    private String cpf;

    @NotBlank
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    private String nome;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getNomeOuRazaoSocial() {
        return this.nome;
    }

    @Override
    public String getCpfOuCnpj() {
        if (cpf != null && cpf.length() == 11) {
            return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        }
        return cpf;
    }
}