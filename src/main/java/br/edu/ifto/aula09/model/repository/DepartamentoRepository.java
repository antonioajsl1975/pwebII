package br.edu.ifto.aula09.model.repository;

import br.edu.ifto.aula09.model.entity.Departamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartamentoRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Departamento departamento) {
        if (departamento.getId() == null) {
            em.persist(departamento);
        } else {
            em.merge(departamento);
        }
    }

    public Departamento departamento(Long id) {
        return em.find(Departamento.class, id);
    }

    public List<Departamento> departamentos() {
        Query query = em.createQuery("from Departamento");
        return query.getResultList();
    }

    @Transactional
    public void deleteById(Long id) {
        Departamento departamento = em.find(Departamento.class, id);
        em.remove(departamento);
    }

    @Transactional
    public void update(Departamento departamento) {
        em.merge(departamento);
    }

    public List<Departamento> findByNome(String nome) {
        String hql = "from Departamento d where lower(d.nome) like lower(:nome)";
        Query query = em.createQuery(hql);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }
}
