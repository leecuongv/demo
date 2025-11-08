package com.dsc.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dsc.demo.model.Item;
import com.dsc.demo.repository.InMemoryItemRepository;


@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final InMemoryItemRepository repository;

    public ItemController(InMemoryItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public String getMethodName(@PathVariable Long id) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
        return "Item ID: " + item.getId() + ", Name: " + item.getName();
    }
    

    @GetMapping
    public List<Item> list() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item entity) {
        Item saved = repository.save(entity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    

    @PutMapping("/{id}")
    public Item update(@PathVariable Long id, @RequestBody Item item) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
        Item toSave = new Item(id, item.getName());
        return repository.save(toSave);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
