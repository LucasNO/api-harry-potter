package com.apiharrypotter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class HouseDto implements Serializable {

    private static final long serialVersionUID = 8144770623383593104L;

    @JsonProperty("_id")
    private String _id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("headOfHouse")
    private String headOfHouse;
    @JsonProperty("school")
    private String school;

}
