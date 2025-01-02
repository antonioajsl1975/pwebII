package br.edu.ifto.aula09.model.repository;

import br.edu.ifto.aula09.model.entity.Pessoa;
import br.edu.ifto.aula09.model.entity.Produto;
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

        if (produto.getId() == null) {
            em.persist(produto);
        } else {
            em.merge(produto);
        }
    }

    public Produto findById(Long id) {
        return em.find(Produto.class, id);
    }

    public List<Produto> findAll() {
        Query query = em.createQuery("from Produto");
        return query.getResultList();
    }

    @Transactional
    public void deleteById(Long id) {
        Produto produto = em.find(Produto.class, id);
        em.remove(produto);
    }

    public List<Produto> findByDescricao(String descricao) {
        String hql = "from Produto p where lower(p.descricao) like lower(:descricao)";
        Query query = em.createQuery(hql);
        query.setParameter("descricao", "%" + descricao + "%");
        return query.getResultList();
    }

    public List<Produto> findAllSorted(String sort, String direction) {
        if (!List.of("id", "descricao", "valor").contains(sort)) {
            sort = "id";
        }
        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            direction = "asc";
        }
        String jpql = "from Produto p ORDER BY p."+ sort + " " + direction;
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }
}
