package com.apiharrypotter.controller;

import com.apiharrypotter.dto.CharacterRequest;
import com.apiharrypotter.dto.CharacterResponse;
import com.apiharrypotter.filter.CharacterFilter;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CharacterControllerTest {

    @Autowired
    private CharacterController controller;

    @Test
    void listar_200(){
        ResponseEntity<List<CharacterResponse>> response = controller.listar(new CharacterFilter());
        Assert.assertTrue(Objects.nonNull(response));
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void buscar_200(){
        ResponseEntity<CharacterResponse> response = controller.buscar(2);
        Assert.assertTrue(Objects.nonNull(response));
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void buscar_500(){
        ResponseEntity<CharacterResponse> response = controller.buscar(10);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void listar_filter_200(){
        CharacterFilter filter = new CharacterFilter();
        filter.setHouse("5a05e2b252f721a3cf2ea33f");
        ResponseEntity<List<CharacterResponse>> response = controller.listar(filter);
        Assert.assertTrue(Objects.nonNull(response));
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void inserir_201(){
        CharacterRequest request =CharacterRequest.builder()
                .house("5a05e2b252f721a3cf2ea33f")
                .name("Dumbledore")
                .patronus("fenix")
                .role("DIRECTOR")
                .build();
        ResponseEntity<CharacterResponse> response = controller.inserir(request);
        Assert.assertTrue(Objects.nonNull(response));
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void editar_200() {
        CharacterRequest request =CharacterRequest.builder()
                .house("5a05e2b252f721a3cf2ea33f")
                .name("Harry")
                .patronus("stag")
                .role("STUDENT")
                .build();

        ResponseEntity<CharacterResponse> response = controller.editar(request);
        Assert.assertTrue(Objects.nonNull(response));
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deletar_200() {
        ResponseEntity response = controller.deletar(1);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}