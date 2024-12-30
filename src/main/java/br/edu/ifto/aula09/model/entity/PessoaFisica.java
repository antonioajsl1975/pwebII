package br.edu.ifto.aula09.model.entity;

import br.edu.ifto.aula09.model.utils.Constraint;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = Constraint.uc_pessoafisica__cpf, columnNames = "cpf")})
@Inheritance(strategy = InheritanceType.JOINED)
public class PessoaFisica extends Pessoa implements Serializable {
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

    @Override
    public String getCpfOuCnpj() {
        return this.cpf;
    }
}