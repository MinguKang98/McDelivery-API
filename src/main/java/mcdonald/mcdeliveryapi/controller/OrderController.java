package mcdonald.mcdeliveryapi.controller;

import lombok.RequiredArgsConstructor;
import mcdonald.mcdeliveryapi.domain.Item;
import mcdonald.mcdeliveryapi.domain.Order;
import mcdonald.mcdeliveryapi.domain.User;
import mcdonald.mcdeliveryapi.service.ItemService;
import mcdonald.mcdeliveryapi.service.OrderService;
import mcdonald.mcdeliveryapi.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/orders/new")
    public String createOrder(Model model) {
        List<User> users = userService.findAllUser();
        List<Item> items = itemService.findAllItem();

        model.addAttribute("users", users);
        model.addAttribute("items", items);
        return "/orders/createOrderForm";
    }

    @PostMapping("/orders/new")
    public String create(@RequestParam("userId") Long userId,
                         @RequestParam("itemId") Long itemId,
                         @RequestParam("count") int count) {
//        if (result.hasErrors()) {
//            return "/orders/createOrderForm";
//        }
        //Requestparam 뒤에 bindingresult error??
        orderService.order(userId, itemId, count);
        return "redirect:/";
    }

    @GetMapping("/orders")
    public String list(Model model) {
        List<Order> orders = orderService.findAllOrder();
        model.addAttribute("orders", orders);
        return "/orders/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancel(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }

    @PostMapping("orders/{orderId}/delivery/start")
    public String startDelivery(@PathVariable("orderId") Long orderId){
        orderService.startDelivery(orderId);
        return "redirect:/orders";
    }

    @PostMapping("orders/{orderId}/delivery/complete")
    public String completeDelivery(@PathVariable("orderId") Long orderId){
        orderService.completeDeliveryAndOrder(orderId);
        return "redirect:/orders";
    }

    @PostMapping("orders/{orderId}/delete")
    public String delete(@PathVariable("orderId") Long orderId){
        orderService.deleteOrder(orderId);
        return "redirect:/orders";
    }
}
