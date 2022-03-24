package mcdonald.mcdeliveryapi.repository;

import lombok.RequiredArgsConstructor;
import mcdonald.mcdeliveryapi.domain.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        em.persist(item);
    }

    public void delete(Item item) {
        em.remove(item);
    }

    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    public Optional<Item> findByName(String name) {
        List<Item> findItem = em.createQuery("select i from Item i where i.name = :name", Item.class)
                .setParameter("name", name)
                .getResultList();
        return findItem.stream().findAny();
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
