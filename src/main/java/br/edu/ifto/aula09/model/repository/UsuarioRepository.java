package br.edu.ifto.aula09.model.repository;

import br.edu.ifto.aula09.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
//    @PersistenceContext
//    EntityManager em;
//
//    @Transactional
//    public void save(Usuario usuario) {
//        if (usuario.getId() == null) {
//            em.persist(usuario);
//        } else {
//            em.merge(usuario);
//        }
//    }
//
//    public Usuario findById(Long id) {
//        return em.find(Usuario.class, id);
//    }
//
//    public List<Usuario> findAll() {
//        Query query = em.createQuery("from Usuario");
//        return query.getResultList();
//    }
//
//    @Transactional
//    public void deleteById(Long id) {
//        Usuario usuario = em.find(Usuario.class, id);
//        em.remove(usuario);
//    }
//
//    public List<Usuario> findAllSorted(String sort, String direction) {
//        if (!List.of("id", "login", "password").contains(sort)) {
//            sort = "id";
//        }
//        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
//            direction = "asc";
//        }
//        String jpql = "from Usuario u ORDER BY u."+ sort + " " + direction;
//        Query query = em.createQuery(jpql);
//        return query.getResultList();
//    }
//
//    public Usuario findByLogin(String login) {
//        return em.find(Usuario.class, login);
//    }
}

