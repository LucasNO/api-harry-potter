package com.apiharrypotter.service;

import com.apiharrypotter.dto.CharacterRequest;
import com.apiharrypotter.dto.CharacterResponse;
import com.apiharrypotter.dto.HouseDto;
import com.apiharrypotter.entity.Character;
import com.apiharrypotter.exception.BusinessException;
import com.apiharrypotter.feign.ApiHarryPotterClient;
import com.apiharrypotter.filter.CharacterFilter;
import com.apiharrypotter.mapper.CharacterMapper;
import com.apiharrypotter.repository.CharacterRepository;
import com.apiharrypotter.specification.CharacterSpecification;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
@Service
public class CharacterService {

    @Autowired
    private CharacterRepository repository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CharacterMapper mapper;

    @Autowired
    private ApiHarryPotterClient client;

    @Cacheable(cacheNames = Character.CACHE_NAME, key="#filter")
    public List<CharacterResponse> listar(CharacterFilter filter) {
        Specification<Character> specification = new CharacterSpecification(filter);
        final List<Character> characters = this.repository.findAll(specification);
        if(characters.isEmpty()){
            throw new BusinessException(messageSource.getMessage("character.nao.encontrado", null, Locale.ROOT));
        }
        return mapper.characterToDto(characters);
    }

    @Cacheable(cacheNames = Character.CACHE_NAME, key="#id")
    public CharacterResponse buscar(Integer id){
        Optional<Character> character = repository.findById(id);
        if(character.isEmpty()){
            Object[] parametros = {id.toString()};
            throw new BusinessException(messageSource.getMessage("character.erro.buscar", parametros, Locale.ROOT));
        }
        return mapper.characterToDto(character.get());
    }

    @CacheEvict(cacheNames = Character.CACHE_NAME, allEntries = true)
    public CharacterResponse salvar(CharacterRequest dto){

            Character character = mapper.dtoToCharacter(dto);
            List<HouseDto> list = retry(dto);
            character.setHouse(list.get(0).get_id());
            character.setPatronus(list.get(0).getHeadOfHouse());
            character.setSchool(list.get(0).getSchool());
        try {
            return mapper.characterToDto(repository.save(character));
        }catch (Exception e){
            log.error(e.getLocalizedMessage(),e);
            throw new BusinessException(messageSource.getMessage("character.erro.salvar", null, Locale.ROOT));
        }
    }

    public CharacterResponse editar(CharacterRequest dto) {
        Character character = mapper.dtoToCharacter(dto);
        return mapper.characterToDto(repository.save(character));
    }

    @CacheEvict(cacheNames = Character.CACHE_NAME, key="#id")
    public void deletar(Integer id) {
        try {
            Character character = repository.getOne(id);
            repository.delete(character);
        }catch (Exception e){
            Object[] parametros = {id.toString()};
            throw new BusinessException(messageSource.getMessage("character.erro.deletar", parametros, Locale.ROOT));
        }
    }

    private List<HouseDto> retry(CharacterRequest dto){
        try{
            RetryConfig config = RetryConfig.custom().maxAttempts(3).waitDuration(Duration.of(2, SECONDS)).build();
            RetryRegistry registry = RetryRegistry.of(config);
            Retry retry = registry.retry("findHouse", config);

            Supplier<List<HouseDto>> findHouseSupplier = () -> client.findHouse(dto.getHouse());

            Supplier<List<HouseDto>> list = Retry.decorateSupplier(retry, findHouseSupplier);

            return list.get();
        }catch (Exception e){
            Object[] parametros = {dto.getHouse()};
            throw new BusinessException(messageSource.getMessage("house.erro.buscar", parametros, Locale.ROOT));
        }
    }
}
