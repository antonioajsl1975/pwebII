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

    public List<Venda> findAll(LocalDateTime dataInicio, LocalDateTime dataFim, Long clienteId) {

        String hql = "from Venda v join fetch v.pessoa where 1=1";

        if (dataInicio != null) {
            hql += " and v.dataVenda >= :dataInicio";
        }
        if (dataFim != null) {
            hql += " and v.dataVenda <= :dataFim";
        }
        if (clienteId != null) {
            hql += " and v.pessoa.id = :clienteId";
        }

        Query query = em.createQuery(hql);

        if (dataInicio != null) {
            query.setParameter("dataInicio", dataInicio);
        }
        if (dataFim != null) {
            query.setParameter("dataFim", dataFim);
        }
        if (clienteId != null) {
            query.setParameter("clienteId", clienteId);
        }
        return query.getResultList();
    }
}
