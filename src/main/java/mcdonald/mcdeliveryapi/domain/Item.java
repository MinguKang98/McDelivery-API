package mcdonald.mcdeliveryapi.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    /* 생성메서드 */
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

}
