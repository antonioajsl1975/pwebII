package br.edu.ifto.aula09.model.repository;

import br.edu.ifto.aula09.model.entity.Pessoa;
import br.edu.ifto.aula09.model.entity.PessoaJuridica;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PessoaJuridicaRepository {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public void save(PessoaJuridica pessoaJuridica) {
        if (pessoaJuridica.getId() == null) {
            em.persist(pessoaJuridica);
        } else {
            em.merge(pessoaJuridica);
        }
    }

    public PessoaJuridica findById(Long id) {
        return em.find(PessoaJuridica.class, id);
    }

    public List<PessoaJuridica> findAll() {
        Query query = em.createQuery("from PessoaJuridica");
        return query.getResultList();
    }

    @Transactional
    public void deleteById(Long id) {
        PessoaJuridica pessoaJuridica = em.find(PessoaJuridica.class, id);
        em.remove(pessoaJuridica);
    }

    public List<PessoaJuridica> findByRazaoSocial(String razaoSocial) {
        String hql = "from PessoaJuridica pj where lower(pj.razaoSocial) like lower(:razaoSocial)";
        Query query = em.createQuery(hql);
        query.setParameter("razaoSocial", "%" + razaoSocial + "%");
        return query.getResultList();
    }

    public List<Pessoa> findAllSorted(String sort, String direction) {
        if (!List.of("id", "razaoSocial", "cpnj", "telefone", "email").contains(sort)) {
            sort = "id";
        }
        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            direction = "asc";
        }
        String jpql = "from PessoaJuridica p ORDER BY p."+ sort + " " + direction;
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }
}

