package br.edu.ifto.aula07.model.repository;

import br.edu.ifto.aula07.model.entity.PessoaFisica;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PessoaFisicaRepository {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public void save(PessoaFisica pessoaFisica) {
        if (pessoaFisica.getId() == null) {
            em.persist(pessoaFisica);
        } else {
            em.merge(pessoaFisica);
        }
    }

    public PessoaFisica findById(Long id) {
        return em.find(PessoaFisica.class, id);
    }

    public List<PessoaFisica> findAll() {
        Query query = em.createQuery("from PessoaFisica");
        return query.getResultList();
    }

    @Transactional
    public void deleteById(Long id) {
        PessoaFisica pessoaFisica = em.find(PessoaFisica.class, id);
        em.remove(pessoaFisica);
    }
}

