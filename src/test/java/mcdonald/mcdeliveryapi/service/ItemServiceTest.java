package mcdonald.mcdeliveryapi.service;

import mcdonald.mcdeliveryapi.domain.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Test
    public void 상품등록() throws Exception {
        //given
        Item item = Item.createItem("빅맥", 5400);

        ///when
        Long saveId = itemService.join(item);

        //then
        Item findItem = itemService.findItemById(saveId);
        assertThat(findItem).isEqualTo(item);
    }

    @Test
    public void 중복_상품_예외() throws Exception {
        //given
        Item item1 = Item.createItem("빅맥", 5400);
        Item item2 = Item.createItem("빅맥", 6500);

        ///when
        itemService.join(item1);

        //then
        assertThrows(IllegalStateException.class, () -> {
            itemService.join(item2);
        });
    }

    @Test
    public void 상품삭제() throws Exception {
        //given
        Item item = Item.createItem("빅맥", 5400);
        Long saveId = itemService.join(item);

        ///when
        itemService.withdrawal(saveId);

        //then
        assertThat(itemService.findItemById(saveId)).isNull();
    }

    @Test
    public void 상품_정보_수정() throws Exception {
        //given
        Item item1 = Item.createItem("빅맥", 5400);
        Item item2 = Item.createItem("1955 버거", 6500);

        ///when
        Long saveId = itemService.join(item1);
        itemService.update(saveId,item2);

        //then
        Item findItem = itemService.findItemById(saveId);
        assertThat(findItem).isEqualTo(item2);
    }
}
