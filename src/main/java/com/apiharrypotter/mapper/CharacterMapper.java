package com.apiharrypotter.mapper;

import com.apiharrypotter.dto.CharacterRequestPost;
import com.apiharrypotter.dto.CharacterRequestPut;
import com.apiharrypotter.dto.CharacterResponse;
import com.apiharrypotter.entity.Character;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface CharacterMapper {

    Character dtoToCharacter(CharacterRequestPost dto);
    Character dtoToCharacter(CharacterRequestPut dto);
    CharacterResponse characterToDto(Character character);
    List<CharacterResponse> characterToDto(List<Character> character);

}
