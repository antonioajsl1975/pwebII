package br.edu.ifto.aula07.model.entity;

import br.edu.ifto.aula07.model.utils.Constraint;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = Constraint.uc_pessoajuridica__cnpj, columnNames = "cnpj")})
public class PessoaJuridica extends Pessoa {
    private String cnpj;
    private String razaoSocial;

    // Getters e setters
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @Override
    public String getNomeOuRazaoSocial() {
        return this.razaoSocial;
    }

    @Override
    public String getCpfOuCnpj() {
        return this.cnpj;
    }
}
