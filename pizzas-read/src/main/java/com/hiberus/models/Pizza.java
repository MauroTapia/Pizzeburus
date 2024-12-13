package com.hiberus.models;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pizza")
@Entity
@Getter
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Setter
    @Column(name = "name")
    private String name;


}
