package com.twinkles.Norbs_Shopping_Mall.web.controller;


import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.CartRequestDto;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.responses.CartResponseDto;
import com.twinkles.Norbs_Shopping_Mall.service.cart.CartService;
import com.twinkles.Norbs_Shopping_Mall.web.exception.NorbsShoppingMallException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<?>addItemToCart(@RequestBody CartRequestDto cartRequestDto) throws NorbsShoppingMallException {
        CartResponseDto cartResponseDto = null;
        cartResponseDto = cartService.addItemToCart(cartRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(cartResponseDto);
    }
}
