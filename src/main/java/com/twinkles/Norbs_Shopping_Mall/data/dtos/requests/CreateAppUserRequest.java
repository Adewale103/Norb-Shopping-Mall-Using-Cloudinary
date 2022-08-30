package com.twinkles.Norbs_Shopping_Mall.data.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
