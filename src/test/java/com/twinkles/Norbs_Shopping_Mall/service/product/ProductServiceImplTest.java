package com.twinkles.Norbs_Shopping_Mall.service.product;

import com.twinkles.Norbs_Shopping_Mall.data.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ProductServiceImplTest {

    @BeforeEach
    void setUp() {
    }
    @Test
    void applyPatchToProductTest() {
        Product product = new Product();
        product.setName("Table top");
        product.setPrice(450);
        product.setDescription("Fanciful table top");
        product.setQuantity(3);


    }
}