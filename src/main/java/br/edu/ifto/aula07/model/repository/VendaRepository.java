package br.edu.ifto.aula07.model.repository;

import br.edu.ifto.aula07.model.entity.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VendaRepository {

    @PersistenceContext
    private EntityManager em;

    public Venda venda(Long id) {
        return em.find(Venda.class, id);
    }

    @Transactional
    public void save(Venda venda) {
        em.persist(venda);
    }

    public Venda findById(Long id) {
        return em.find(Venda.class, id);
    }

    @Transactional
    public void remove(Long id) {
        Venda venda = em.find(Venda.class, id);
        em.remove(venda);
    }

    @Transactional
    public void update(Venda Venda) {
        em.merge(Venda);
    }

    public List<Venda> findAll(LocalDateTime dataInicio, LocalDateTime dataFim) {

        String hql = "SELECT v FROM Venda v JOIN FETCH v.pessoa WHERE 1=1";

        if (dataInicio != null) {
            hql += " AND v.dataVenda >= :dataInicio";
        }
        if (dataFim != null) {
            hql += " AND v.dataVenda <= :dataFim";
        }

        Query query = em.createQuery(hql);

        if (dataInicio != null) {
            query.setParameter("dataInicio", dataInicio);
        }
        if (dataFim != null) {
            query.setParameter("dataFim", dataFim);
        }
        return query.getResultList();
    }
}
