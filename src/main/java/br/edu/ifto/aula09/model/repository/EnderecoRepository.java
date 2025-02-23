package br.edu.ifto.aula09.model.repository;

import br.edu.ifto.aula09.model.entity.Endereco;
import br.edu.ifto.aula09.model.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Pessoa> findByPessoas(Pessoa pessoa);
    List<Endereco> findByCepAndNumero(String cep, String numero);
    List<Endereco> findByPessoas_Id(Long pessoaId);
}
