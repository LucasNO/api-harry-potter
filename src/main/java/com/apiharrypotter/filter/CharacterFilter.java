package com.apiharrypotter.filter;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CharacterFilter implements Serializable {

    private static final long serialVersionUID = -1653433507017441884L;

    private String house;
}
