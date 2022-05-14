package mcdonald.mcdeliveryapi.controller;

import lombok.Getter;
import lombok.Setter;
import mcdonald.mcdeliveryapi.domain.Item;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class ItemDTO {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    @Positive(message = "상품의 가격은 항상 0 이상의 값입니다.")
    private int price;

    public Item toItem() {
        Item item = Item.createItem(name, price);
        return item;
    }
}
