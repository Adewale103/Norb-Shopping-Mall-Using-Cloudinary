package com.twinkles.Norbs_Shopping_Mall.data.model;

import com.twinkles.Norbs_Shopping_Mall.data.model.enums.RoleType;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Entity
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser {
    @Id
    @SequenceGenerator(name = "user_id_sequence", allocationSize = 1, sequenceName = "user_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
    private Long id;
    private String firstName;
    private String lastName;
    @Email
    @Column(unique = true)
    private String email;
    private String password;

    @Column(length = 500)
    private String address;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Cart myCart;

    public AppUser(String firstName, String lastName, String email, String password){
        myCart = new Cart();
        myCart.setTotalPrice(0.00);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        if(roles == null){
            roles = new HashSet<>();
        }
        roles.add(new Role(RoleType.ROLE_USER));
    }

    public AppUser(String firstName, String lastName, String email, String password, RoleType roleType){
        myCart = new Cart();
        myCart.setTotalPrice(0.00);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        if(roles == null){
            roles = new HashSet<>();
        }
        roles.add(new Role(roleType));
    }
}
