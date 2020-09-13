package com.apiharrypotter.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharacterRequest {

    private Integer id;
    private String name;
    private String role;
    private String house;
    private String patronus;
}
