package com.dsc.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.dsc.demo.model.Item;

@Component
public class InMemoryItemRepository {

    private final Map<Long, Item> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(0);

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Item save(Item item) {
        if (item.getId() == null) {
            item.setId(idGen.incrementAndGet());
        }
        store.put(item.getId(), item);
        return item;
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    public boolean existsById(Long id) {
        return store.containsKey(id);
    }
}
