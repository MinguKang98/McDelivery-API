package mcdonald.mcdeliveryapi.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    // image
}
