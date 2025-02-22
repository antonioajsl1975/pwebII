package br.edu.ifto.aula09.model.repository;

import br.edu.ifto.aula09.model.entity.TipoEndereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEnderecoRepository extends JpaRepository<TipoEndereco, Long> {
}
