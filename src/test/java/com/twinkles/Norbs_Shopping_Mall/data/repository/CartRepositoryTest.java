package com.twinkles.Norbs_Shopping_Mall.data.repository;

import com.twinkles.Norbs_Shopping_Mall.data.model.Cart;
import com.twinkles.Norbs_Shopping_Mall.data.model.Item;
import com.twinkles.Norbs_Shopping_Mall.data.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Sql(scripts = {"/db/insert.sql"})
@Slf4j
class CartRepositoryTest {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void addProduct(){
        Product product = productRepository.findByName("MacBook Air").orElse(null);
        assertThat(product).isNotNull();

        Item item = new Item(product,2);
        Cart cart = new Cart();
        cart.addItem(item);

        cartRepository.save(cart);
        assertThat(cart.getId()).isNotNull();
        log.info("Cart -> {}",cart);
    }
    @Test
    @Transactional
    void viewItemsInCartTest(){
        Cart foundCart = cartRepository.findById(345L).orElse(null);
        assertThat(foundCart).isNotNull();
        assertThat(foundCart.getItems().size()).isEqualTo(2);
        log.info("Cart -> {}",foundCart);
    }



}