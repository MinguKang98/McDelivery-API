package mcdonald.mcdeliveryapi.controller;

import lombok.RequiredArgsConstructor;
import mcdonald.mcdeliveryapi.domain.Item;
import mcdonald.mcdeliveryapi.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createItem(Model model) {
        model.addAttribute("itemDTO", new ItemDTO());
        return "/items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@Valid ItemDTO itemDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "items/createItemForm";
        }
        Item item = itemDTO.toItem();
        itemService.join(item);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findAllItem();
        model.addAttribute("items", items);
        return "/items/itemList";
    }

    @GetMapping("/items/{itemId}/update")
    public String updateItem(@PathVariable("itemId") Long itemId, Model model) {
        Item findItem = itemService.findItemById(itemId);
        ItemDTO itemDTO = findItem.toItemDTO();
        model.addAttribute("itemDTO", itemDTO);
        return "/items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/update")
    public String update(@PathVariable("itemId") Long itemId, @Valid ItemDTO itemDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "/items/updateItemForm";
        }
        Item newItem =itemDTO.toItem();
        itemService.update(itemId, newItem);
        return "redirect:/items";
    }

    @GetMapping("/items/{itemId}/delete")
    public String deleteItem(@PathVariable("itemId") Long itemId) {
        Item findItem = itemService.findItemById(itemId);
        itemService.withdrawal(itemId);
        return "redirect:/items";
    }
}
