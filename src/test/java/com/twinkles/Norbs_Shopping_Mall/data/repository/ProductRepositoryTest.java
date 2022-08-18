package com.twinkles.Norbs_Shopping_Mall.data.repository;

import com.twinkles.Norbs_Shopping_Mall.data.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Slf4j
@Sql(scripts={"/db/insert.sql"})
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("Save a product to database")
    void saveAProductToDatabaseTest(){
        Product product = new Product();
        product.setName("Bamboo chair");
        product.setDescription("World class bamboo");
        product.setPrice(12345);
        product.setQuantity(9);

        assertThat(product.getId()).isNull();

        productRepository.save(product);
        log.info("Product -> {}",product);

        assertThat(product.getId()).isNotNull();
        assertThat(product.getName()).isEqualTo("Bamboo chair");
        assertThat(product.getPrice()).isEqualTo(12345);
        assertThat(product.getQuantity()).isEqualTo(9);
    }

    @Test
    @DisplayName("Find product by Id")
    void findProductFromDatabaseTest(){
        Product product = productRepository.findById(12L).orElse(null);
        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getId()).isNotNull();
        Assertions.assertThat(product.getName()).isEqualTo("Luxury Map");
        Assertions.assertThat(product.getPrice()).isEqualTo(2340);
        Assertions.assertThat(product.getQuantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("Find all products in the database")
    void findAllProducts(){
        List<Product> productList = productRepository.findAll();
        assertThat(productList).isNotNull();
        assertThat(productList.size()).isEqualTo(4);

    }

    @Test
    @DisplayName("Find product by name")
    void findProductByName(){
        Product product = productRepository.findByName("Rocking chair").orElse(null);
        assertThat(product).isNotNull();
        assertThat(product.getQuantity()).isEqualTo(5);
        assertThat(product.getPrice()).isEqualTo(4140);
    }

    @Test
    @DisplayName("update product attribute")
    void updateProductAttributeTest() {
        Product savedProduct = productRepository.findByName("Macbook Air").orElse(null);
        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getName()).isEqualTo("Macbook Air");
        Assertions.assertThat(savedProduct.getPrice()).isEqualTo(1340);

        savedProduct.setName("Macbook Air11");
        savedProduct.setPrice(1450);

        productRepository.save(savedProduct);

        assertThat(savedProduct.getName()).isEqualTo("Macbook Air11");
        assertThat(savedProduct.getPrice()).isEqualTo(1450);
    }
}