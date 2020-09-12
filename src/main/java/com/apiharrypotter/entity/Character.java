package com.apiharrypotter.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "character")
public class Character implements Serializable {

    private static final long serialVersionUID = 5469297829672696666L;

    public static final String CACHE_NAME = "Character";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @Column(name = "school")
    private String school;

    @Column(name = "house")
    private String house;

    @Column(name = "patronus")
    private String patronus;

}
