package com.twinkles.Norbs_Shopping_Mall.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    private double price;

    @CreationTimestamp
    private Date dateCreated;
    private String imageUrl;
    private int quantity;

}
