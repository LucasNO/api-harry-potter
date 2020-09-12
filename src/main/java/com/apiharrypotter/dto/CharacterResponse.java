package com.apiharrypotter.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterResponse implements Serializable {

    private static final long serialVersionUID = -2039086049099486281L;

    private Integer id;
    private String name;
    private String role;
    private String school;
    private String house;
    private String patronus;
}
