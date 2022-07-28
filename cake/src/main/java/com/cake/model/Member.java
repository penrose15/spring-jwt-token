package com.cake.model;

import com.cake.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "member_role",
    joinColumns = @JoinColumn(name ="user_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name ="role_id",referencedColumnName = "id"))
    private List<Role> roles;

}
