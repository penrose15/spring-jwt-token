package com.cake.role;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Privilage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorities;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;



}
