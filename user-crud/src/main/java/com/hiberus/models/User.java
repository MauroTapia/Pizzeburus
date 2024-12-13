package com.hiberus.models;

import lombok.*;
import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
@Getter
@Setter
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Setter
    @Column(name = "name")
    private String name;
    @Setter
    @Column(name = "pizzasFav")
    private String pizzafav;


}