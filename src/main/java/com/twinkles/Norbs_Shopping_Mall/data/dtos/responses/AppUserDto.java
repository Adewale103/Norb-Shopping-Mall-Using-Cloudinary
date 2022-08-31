package com.twinkles.Norbs_Shopping_Mall.data.dtos.responses;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.validation.constraints.Email;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class AppUserDto {
    private long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Email
    @Column(unique = true)
    private String email;
    private String password;

    @Column(length = 500)
    private String address;
}
