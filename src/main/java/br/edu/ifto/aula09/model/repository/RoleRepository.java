package br.edu.ifto.aula09.model.repository;

import br.edu.ifto.aula09.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNome(String nome);
}
