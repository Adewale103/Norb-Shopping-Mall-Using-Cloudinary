package com.twinkles.Norbs_Shopping_Mall.data.dtos.requests;

import com.twinkles.Norbs_Shopping_Mall.data.dtos.QuantityOperation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartUpdateDto {
    private Long userId;
    private Long itemId;
    private QuantityOperation quantityOp;
}
