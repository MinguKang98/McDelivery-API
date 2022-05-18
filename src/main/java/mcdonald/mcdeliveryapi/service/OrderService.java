package mcdonald.mcdeliveryapi.service;

import lombok.RequiredArgsConstructor;
import mcdonald.mcdeliveryapi.domain.*;
import mcdonald.mcdeliveryapi.repository.ItemRepository;
import mcdonald.mcdeliveryapi.repository.OrderRepository;
import mcdonald.mcdeliveryapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long order(Long userId, Long itemId, int count) {
        // user
        User findUser = userRepository.findById(userId);

        //item & orderItem
        Item findItem = itemRepository.findById(itemId);
        OrderItem orderItem = OrderItem.createOrderItem(findItem, findItem.getPrice(), count);

        //delivery
        Delivery delivery = Delivery.createDelivery(DeliveryStatus.READY, findUser.getAddress());

        //order
        Order order = Order.createOrder(findUser, delivery, OrderStatus.READY, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id);
        if (order.getStatus() == OrderStatus.READY && order.getDelivery().getStatus() == DeliveryStatus.READY) {
            order.changeOrderStatus(OrderStatus.CANCEL);
        }
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id);
        orderRepository.delete(order);
    }

    @Transactional
    public void startDelivery(Long id) {
        Order order = orderRepository.findById(id);
        if(order.getStatus()==OrderStatus.READY){
            order.getDelivery().changeDeliveryStatus(DeliveryStatus.RUNNING);
        }
    }

    @Transactional
    public void completeDeliveryAndOrder(Long id) {
        Order order = orderRepository.findById(id);
        if (order.getStatus() == OrderStatus.READY && order.getDelivery().getStatus() == DeliveryStatus.RUNNING) {
            order.getDelivery().changeDeliveryStatus(DeliveryStatus.COMPLETE);
            order.changeOrderStatus(OrderStatus.DONE);
        }
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findAllOrder() {
        return orderRepository.findAll();
    }
}
