package com.twinkles.Norbs_Shopping_Mall.service.cart;

import com.twinkles.Norbs_Shopping_Mall.data.dtos.QuantityOperation;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.CartRequestDto;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.CartUpdateDto;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.responses.CartResponseDto;
import com.twinkles.Norbs_Shopping_Mall.data.model.AppUser;
import com.twinkles.Norbs_Shopping_Mall.data.model.Cart;
import com.twinkles.Norbs_Shopping_Mall.data.model.Item;
import com.twinkles.Norbs_Shopping_Mall.data.repository.AppUserRepository;
import com.twinkles.Norbs_Shopping_Mall.data.repository.CartRepository;
import com.twinkles.Norbs_Shopping_Mall.data.repository.ProductRepository;
import com.twinkles.Norbs_Shopping_Mall.web.exception.NorbsShoppingMallException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"/db/insert.sql"})
@Slf4j
class CartServiceImplTest {
    @Autowired
    private CartService cartService;

    @Autowired
    private AppUserRepository appUserRepository;

    CartUpdateDto cartUpdateDto;


    @BeforeEach
    void setUp() {
       cartUpdateDto = CartUpdateDto.builder()
               .itemId(102L)
               .quantityOp(QuantityOperation.INCREASE)
               .userId(5005L).build();
    }
    @Test
    @DisplayName("Add Item Test")
    void itemCanBeAddedTest() throws NorbsShoppingMallException {
        CartRequestDto cartRequestDto = new CartRequestDto();
        cartRequestDto.setProductId(13L);
        cartRequestDto.setAppUserId(5011L);
        cartRequestDto.setQuantity(1);

        CartResponseDto response = cartService.addItemToCart(cartRequestDto);
        assertThat(response.getTotalPrice()).isNotNull();
        assertThat(response.getItemList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Get cart price")
    void cartPriceCanBeGottenTest() throws NorbsShoppingMallException {
        CartRequestDto cartRequestDto = new CartRequestDto();
        cartRequestDto.setProductId(13L);
        cartRequestDto.setAppUserId(5011L);
        cartRequestDto.setQuantity(2);

        CartResponseDto response = cartService.addItemToCart(cartRequestDto);
        assertThat(response.getItemList().size()).isEqualTo(1);
        assertThat(response.getTotalPrice()).isEqualTo(2680);
    }
    @Test
    @DisplayName("Update cart item details")
    void cartItemDetailsCanBeUpdated() throws NorbsShoppingMallException {
        Optional<AppUser> appUser = appUserRepository.findById(5005L);
        AppUser foundUser = appUser.get();
        Cart userCart = foundUser.getMyCart();
        Item item = userCart.getItems().get(0);
        assertThat(item.getQuantityAddedToCart()).isEqualTo(2);

        CartResponseDto responseDto = cartService.updateCartItem(cartUpdateDto);
        assertThat(responseDto.getItemList().get(0).getQuantityAddedToCart()).isEqualTo(3);
    }

}