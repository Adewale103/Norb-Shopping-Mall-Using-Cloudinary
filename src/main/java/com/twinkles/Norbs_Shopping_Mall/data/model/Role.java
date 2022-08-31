package com.twinkles.Norbs_Shopping_Mall.data.model;

import com.twinkles.Norbs_Shopping_Mall.data.model.enums.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Role {
    @Id
    @SequenceGenerator(name = "role_id_sequence", sequenceName = "role_id_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "role_id_sequence")
    public Long id;
    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    public Role(RoleType roleType){
        this.roleType = roleType;
    }

}
