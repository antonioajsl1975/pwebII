package br.edu.ifto.aula07.model.repository;

import br.edu.ifto.aula07.model.entity.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutoRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Produto produto) {
        em.merge(produto);
    }

    public Produto produto(Long id) {
        return em.find(Produto.class, id);
    }

    public List<Produto> produtos() {
        Query query = em.createQuery("from Produto");
        return query.getResultList();
    }

    @Transactional
    public void deleteById(Long id) {
        Produto produto = em.find(Produto.class, id);
        em.remove(produto);
    }

    @Transactional
    public void update(Produto produto) {
        em.merge(produto);
    }
}
