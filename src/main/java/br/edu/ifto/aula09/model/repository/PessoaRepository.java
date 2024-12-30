package br.edu.ifto.aula09.model.repository;

import br.edu.ifto.aula09.model.entity.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PessoaRepository {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public void save(Pessoa pessoa) {
        if (pessoa.getId() == null) {
            em.persist(pessoa);
        } else {
            em.merge(pessoa);
        }
    }

    public Pessoa findById(Long id) {
        return em.find(Pessoa.class, id);
    }

    public List<Pessoa> findAll() {
        Query query = em.createQuery("from Pessoa");
        return query.getResultList();
    }

    @Transactional
    public void deleteById(Long id) {
        Pessoa pessoa = em.find(Pessoa.class, id);
        em.remove(pessoa);
    }
}

