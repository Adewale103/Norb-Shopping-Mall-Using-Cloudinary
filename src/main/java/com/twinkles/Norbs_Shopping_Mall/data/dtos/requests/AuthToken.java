package com.twinkles.Norbs_Shopping_Mall.data.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    private String token;
    private Long id;
}
