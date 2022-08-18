package com.twinkles.Norbs_Shopping_Mall.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twinkles.Norbs_Shopping_Mall.data.model.Product;
import com.twinkles.Norbs_Shopping_Mall.data.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/insert.sql"})
public class ProductControllerTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Get products from api")
    void getProductsTest() throws Exception{
        mockMvc.perform(get("/api/product")
                        .contentType("application/json"))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    @DisplayName("Create a product api")
    void createProductTest() throws Exception{
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post("/api/product")
                .param("name", "Bamboo chair")
                .param("description", "World class bamboo")
                .param("price", "540")
                .param("quantity", "5");

        mockMvc.perform(request
                        .contentType("multipart/form-data"))
                .andExpect(status().is(201))
                .andDo(print());
    }
    @Test
    void updateProductTest() throws Exception {
        Product product = productRepository.findById(14L).orElse(null);
        assertThat(product).isNotNull();

        mockMvc.perform(patch("/api/product/14")
                        .contentType("application/json-patch+json")
                        .content(Files.readAllBytes(Path.of("payload.json"))))
                .andExpect(status().is(200))
                .andDo(print());

        product = productRepository.findById(14L).orElse(null);
        assertThat(product).isNotNull();
        assertThat(product.getDescription()).isEqualTo("Luxury at its real best");


    }
}
