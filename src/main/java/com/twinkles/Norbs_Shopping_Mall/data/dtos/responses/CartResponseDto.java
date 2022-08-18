package com.twinkles.Norbs_Shopping_Mall.data.dtos.responses;

import com.twinkles.Norbs_Shopping_Mall.data.model.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartResponseDto {
    private List<Item> itemList;
    private double totalPrice;
}
