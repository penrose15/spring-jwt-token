package com.cake.role;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @OneToMany(mappedBy = "role")
    private List<Privilage> privilages;

    public void addPrivlilage(Privilage privilage) {
        if(!this.privilages.contains(privilage)) {
            privilages.add(privilage);
        }
    }

}
