package mcdonald.mcdeliveryapi.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import mcdonald.mcdeliveryapi.controller.ItemDTO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter(value = AccessLevel.PRIVATE)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    /* 생성자 메서드 */
    public static Item createItem(String name, int price) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);

        return item;
    }

    public void changeItemValues(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return getPrice() == item.getPrice() && Objects.equals(getName(), item.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPrice());
    }

    public ItemDTO toItemDTO() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(name);
        itemDTO.setPrice(price);
        return  itemDTO;
    }
}
