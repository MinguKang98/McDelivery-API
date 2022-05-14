package mcdonald.mcdeliveryapi.service;

import lombok.RequiredArgsConstructor;
import mcdonald.mcdeliveryapi.domain.Item;
import mcdonald.mcdeliveryapi.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public Long join(Item item) {
        validateDuplicateItem(item);
        itemRepository.save(item);
        return item.getId();
    }

    private void validateDuplicateItem(Item item) {
        Optional<Item> findItem = itemRepository.findByName(item.getName());
        if (findItem.isPresent()) {
            throw new IllegalStateException("이미 존재하는 상품입니다.");
        }
    }

    public Item findItemById(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> findAllItem() {
        return itemRepository.findAll();
    }

    @Transactional
    public void withdrawal(Long id) {
        Item findItem = itemRepository.findById(id);
        itemRepository.delete(findItem);
    }

    @Transactional
    public void update(Long id, Item item) {
        Item findItem = itemRepository.findById(id);
        findItem.changeItemValues(item);
    }
}
