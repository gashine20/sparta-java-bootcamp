package com.example.locking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void updateItemQuantity(Long id, Integer newQuantity) {
        Item item = itemRepository.findByIdWithLock(id);

        item.setQuantity(newQuantity);
        itemRepository.save(item);
    }

    @Transactional
    public Item findItemById(Long itemId){
        return itemRepository.findById(itemId).orElse(null);
    }
}
