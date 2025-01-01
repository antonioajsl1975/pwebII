package br.edu.ifto.aula09.model.entity;

import br.edu.ifto.aula09.model.utils.Constraint;
import br.edu.ifto.aula09.model.utils.ValidCNPJ;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = Constraint.uc_pessoajuridica__cnpj, columnNames = "cnpj")})
public class PessoaJuridica extends Pessoa implements Serializable {
    @NotBlank
    @ValidCNPJ
    private String cnpj;

    @NotBlank
    @Size(min = 3, max = 50)
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
        if (cnpj != null && cnpj.length() == 14) {
            return cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        }
        return cnpj;
    }
}
