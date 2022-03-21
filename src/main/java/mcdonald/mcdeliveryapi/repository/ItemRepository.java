package mcdonald.mcdeliveryapi.repository;

import lombok.RequiredArgsConstructor;
import mcdonald.mcdeliveryapi.domain.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public void delete(Item item) {
        em.remove(item);
    }

    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    /*
    이름이 중복 가능한 User 와 달리 Item 은 이름이 중복되지 않으므로 단일값 반환
     */
    public Item findByName(String name) {
        return em.createQuery("select i from Item i where i.name = :name", Item.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
