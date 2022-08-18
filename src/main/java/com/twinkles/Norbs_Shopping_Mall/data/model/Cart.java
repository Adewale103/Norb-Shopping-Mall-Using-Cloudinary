package com.twinkles.Norbs_Shopping_Mall.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private double totalPrice;
    
    public void addItem(Item item){
        if(items == null ){
            items = new ArrayList<>();
        }
        items.add(item);
    }
    

}
