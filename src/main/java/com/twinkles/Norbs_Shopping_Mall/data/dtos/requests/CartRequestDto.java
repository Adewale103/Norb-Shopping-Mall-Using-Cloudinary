package com.twinkles.Norbs_Shopping_Mall.data.dtos.requests;

import lombok.Builder;
import lombok.Data;

@Data
public class CartRequestDto {
    private Long appUserId;
    private Long productId;
    private Integer quantity;
}
