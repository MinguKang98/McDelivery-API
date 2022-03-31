package mcdonald.mcdeliveryapi.service;

import mcdonald.mcdeliveryapi.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    ItemService itemService;
    @Autowired
    UserService userService;

    @Test
    public void 주문등록() throws Exception {
        //given
        User user = User.createUser("kang", "mintgreen", "kang@naver.com", "1234",
                new Address("서울시", "한천로37길 33", "101-101"));
        Item item = Item.createItem("빅맥", 5400);
        Long userSaveId = userService.join(user);
        Long itemSaveId = itemService.join(item);


        ///when
        Long orderSaveId = orderService.order(userSaveId, itemSaveId, 3);

        //then
        Order findOrder = orderService.findOrderById(orderSaveId);
        assertThat(findOrder.getUser()).isEqualTo(user);
        assertThat(findOrder.getOrderItems().get(0).getItem()).isEqualTo(item);
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        User user = User.createUser("kang", "mintgreen", "kang@naver.com", "1234",
                new Address("서울시", "한천로37길 33", "101-101"));
        Item item = Item.createItem("빅맥", 5400);
        Long userSaveId = userService.join(user);
        Long itemSaveId = itemService.join(item);
        Long orderSaveId = orderService.order(userSaveId, itemSaveId, 3);

        ///when
        orderService.cancelOrder(orderSaveId);

        //then
        Order findOrder = orderService.findOrderById(orderSaveId);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    @Test
    public void 주문삭제() throws Exception {
        //given
        User user = User.createUser("kang", "mintgreen", "kang@naver.com", "1234",
                new Address("서울시", "한천로37길 33", "101-101"));
        Item item = Item.createItem("빅맥", 5400);
        Long userSaveId = userService.join(user);
        Long itemSaveId = itemService.join(item);
        Long orderSaveId = orderService.order(userSaveId, itemSaveId, 3);

        ///when
        orderService.deleteOrder(orderSaveId);

        //then
        Order findOrder = orderService.findOrderById(orderSaveId);
        assertThat(findOrder).isNull();
    }

    @Test
    public void 배달시작() throws Exception {
        //given
        User user = User.createUser("kang", "mintgreen", "kang@naver.com", "1234",
                new Address("서울시", "한천로37길 33", "101-101"));
        Item item = Item.createItem("빅맥", 5400);
        Long userSaveId = userService.join(user);
        Long itemSaveId = itemService.join(item);
        Long orderSaveId = orderService.order(userSaveId, itemSaveId, 3);

        ///when
        orderService.startDelivery(orderSaveId);

        //then
        Order findOrder = orderService.findOrderById(orderSaveId);
        assertThat(findOrder.getDelivery().getStatus()).isEqualTo(DeliveryStatus.RUNNING);
    }

    @Test
    public void 배달_주문완료() throws Exception {
        //given
        User user = User.createUser("kang", "mintgreen", "kang@naver.com", "1234",
                new Address("서울시", "한천로37길 33", "101-101"));
        Item item = Item.createItem("빅맥", 5400);
        Long userSaveId = userService.join(user);
        Long itemSaveId = itemService.join(item);
        Long orderSaveId = orderService.order(userSaveId, itemSaveId, 3);

        ///when
        orderService.completeDeliveryAndOrder(orderSaveId);

        //then
        Order findOrder = orderService.findOrderById(orderSaveId);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.DONE);
        assertThat(findOrder.getDelivery().getStatus()).isEqualTo(DeliveryStatus.COMPLETE);
    }
}
