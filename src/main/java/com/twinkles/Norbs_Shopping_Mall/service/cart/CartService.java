package com.twinkles.Norbs_Shopping_Mall.service.cart;

import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.CartRequestDto;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.CartUpdateDto;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.responses.CartResponseDto;
import com.twinkles.Norbs_Shopping_Mall.web.exception.NorbsShoppingMallException;

public interface CartService {
    CartResponseDto addItemToCart(CartRequestDto requestDto) throws NorbsShoppingMallException;
    CartResponseDto updateCartItem(CartUpdateDto cartUpdateDto) throws NorbsShoppingMallException;

}
