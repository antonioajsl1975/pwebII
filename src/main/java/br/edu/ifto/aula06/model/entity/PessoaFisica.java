package br.edu.ifto.aula06.model.entity;

import br.edu.ifto.aula06.model.utils.Constraint;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = {@UniqueConstraint(name = Constraint.uc_pessoafisica__cpf, columnNames = "cpf")})
public class PessoaFisica extends Pessoa {
    private String cpf;
    private String nome;

    // Getters e setters
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
}