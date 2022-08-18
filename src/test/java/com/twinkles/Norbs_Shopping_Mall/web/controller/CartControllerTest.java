package com.twinkles.Norbs_Shopping_Mall.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.CartRequestDto;
import com.twinkles.Norbs_Shopping_Mall.web.exception.NorbsShoppingMallException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@Sql(scripts = {"/db/insert.sql"})
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Add a new item to Cart")
    void addItemToCart() throws Exception {
        mapper = new ObjectMapper();
        CartRequestDto cartRequestDto = new CartRequestDto();
        cartRequestDto.setAppUserId(5011L);
        cartRequestDto.setProductId(14L);
        cartRequestDto.setQuantity(1);

        mockMvc.perform(post("/api/cart")
                .contentType("application/json")
                .content(mapper.writeValueAsString(cartRequestDto)))
                .andExpect(status().isOk())
                .andDo(print());


    }

}
