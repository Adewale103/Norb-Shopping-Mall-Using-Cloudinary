package com.twinkles.Norbs_Shopping_Mall.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@Entity
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String LastName;
    @Column(unique = true)
    private String email;

    @Column(length = 500)
    private String address;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Cart myCart;

    public AppUser(){
        myCart = new Cart();
        myCart.setTotalPrice(0.00);
    }
}
