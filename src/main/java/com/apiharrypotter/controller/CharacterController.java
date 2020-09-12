package com.apiharrypotter.controller;

import com.apiharrypotter.dto.CharacterRequest;
import com.apiharrypotter.dto.CharacterResponse;
import com.apiharrypotter.filter.CharacterFilter;
import com.apiharrypotter.service.CharacterService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/characters")
@Api(value = "CharacterController - Controller responsável por funções do Character")
public class CharacterController {

    private static final String MAIN_SERVICE = "mainService";

    @Autowired
    private CharacterService service;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    @ApiOperation("Lista os characters cadastrados com filtro por house.")
    public ResponseEntity<List<CharacterResponse>> listar(final CharacterFilter filter){
        return ResponseEntity.ok(this.service.listar(filter));
    }

    @GetMapping("/{id}")
    @ApiOperation("Busca um character pelo id.")
    @CircuitBreaker(name = MAIN_SERVICE, fallbackMethod="testFallBack")
    public ResponseEntity<CharacterResponse> buscar(@PathVariable("id") Integer id){
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    @ApiOperation("Insere character enviando formulário pelo corpo da requisição.")
    public ResponseEntity<CharacterResponse> inserir(@RequestBody CharacterRequest form){
        CharacterResponse response = service.salvar(form);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation("Edita character enviando formulário pelo corpo da requisição.")
    public ResponseEntity<CharacterResponse> editar(@RequestBody CharacterRequest form){
        CharacterResponse response = service.editar(form);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deleta character enviando o id pela url.")
    public ResponseEntity deletar(@PathVariable("id") Integer id){
        service.deletar(id);
        Object[] parametros = {id.toString()};
        return ResponseEntity.ok().body(messageSource.getMessage("character.sucesso.deletar", parametros, Locale.ROOT));
    }

    private  ResponseEntity<String> testFallBack(Exception e){
        return new ResponseEntity<>("Fallback Method: "+messageSource.getMessage("character.nao.encontrado", null, Locale.ROOT), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
