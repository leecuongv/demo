package com.dsc.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.dsc.demo.model.Item;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void createAndGet() {
        Item toCreate = new Item();
        toCreate.setName("hello");

    org.springframework.http.ResponseEntity<Item> post = rest.postForEntity("/api/items", toCreate, Item.class);
    Assertions.assertEquals(org.springframework.http.HttpStatus.CREATED, post.getStatusCode());
        Item created = post.getBody();
        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getId());
        Assertions.assertEquals("hello", created.getName());

    org.springframework.http.ResponseEntity<Item> get = rest.getForEntity("/api/items/" + created.getId(), Item.class);
    Assertions.assertEquals(org.springframework.http.HttpStatus.OK, get.getStatusCode());
        Assertions.assertNotNull(get.getBody());
        Assertions.assertEquals(created.getId(), get.getBody().getId());
    }
}
