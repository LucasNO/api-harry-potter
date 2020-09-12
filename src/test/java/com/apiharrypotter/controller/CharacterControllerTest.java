package com.apiharrypotter.controller;

import com.apiharrypotter.dto.CharacterRequest;
import com.apiharrypotter.dto.CharacterResponse;
import com.apiharrypotter.repository.CharacterRepository;
import com.apiharrypotter.service.CharacterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CharacterControllerTest {

    private final String BASE_URL = "/characters";

    @Autowired
    private CharacterController controller;

    private MockMvc mockMvc;

    @MockBean
    private CharacterService service;

    @MockBean
    private CharacterRepository repository;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void listar_200() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void buscar_200() throws Exception {
        CharacterResponse character = CharacterResponse.builder()
                .id(1)
                .name("Harry")
                .role("STUDENT")
                .school("Hogwarts School of Witchcraft and Wizardry")
                .house("5a05e2b252f721a3cf2ea33f")
                .patronus("Minerva McGonagall")
                .build();
        when(this.service.buscar(1)).thenReturn(character);

        mockMvc.perform(get(BASE_URL+"/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).buscar(1);
    }

    @Test
    void buscar_400() throws Exception {
        mockMvc.perform(get(BASE_URL+"/a"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void inserir_201() throws Exception {
        CharacterRequest request = CharacterRequest.builder()
                .name("Harry")
                .role("STUDENT")
                .house("5a05e2b252f721a3cf2ea33f")
                .build();

        CharacterResponse response = CharacterResponse.builder()
                .id(1)
                .name("Harry")
                .role("STUDENT")
                .school("Hogwarts School of Witchcraft and Wizardry")
                .house("5a05e2b252f721a3cf2ea33f")
                .patronus("Minerva McGonagall")
                .build();

        when(service.salvar(any(CharacterRequest.class))).thenReturn(response);

        mockMvc.perform(post(BASE_URL)
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Harry")))
                .andExpect(jsonPath("$.role", is("STUDENT")))
                .andExpect(jsonPath("$.school", is("Hogwarts School of Witchcraft and Wizardry")))
                .andExpect(jsonPath("$.house", is("5a05e2b252f721a3cf2ea33f")))
                .andExpect(jsonPath("$.patronus", is("Minerva McGonagall")));

        verify(service, times(1)).salvar(any(CharacterRequest.class));
    }

    @Test
    void editar_200() throws Exception {
        CharacterRequest request = CharacterRequest.builder()
                .name("Harry")
                .role("STUDENT")
                .house("5a05e2b252f721a3cf2ea33f")
                .build();

        CharacterResponse response = CharacterResponse.builder()
                .id(1)
                .name("Harry")
                .role("STUDENT")
                .school("Hogwarts School of Witchcraft and Wizardry")
                .house("5a05e2b252f721a3cf2ea33f")
                .patronus("Minerva McGonagall")
                .build();

        when(service.editar(any(CharacterRequest.class))).thenReturn(response);

        mockMvc.perform(put(BASE_URL)
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Harry")))
                .andExpect(jsonPath("$.role", is("STUDENT")))
                .andExpect(jsonPath("$.school", is("Hogwarts School of Witchcraft and Wizardry")))
                .andExpect(jsonPath("$.house", is("5a05e2b252f721a3cf2ea33f")))
                .andExpect(jsonPath("$.patronus", is("Minerva McGonagall")));

        verify(service, times(1)).editar(any(CharacterRequest.class));

    }

    @Test
    void deletar_200() throws Exception {
        mockMvc.perform(delete(BASE_URL+"/1"))
                .andExpect(status().isOk());
        verify(service, times(1)).deletar(1);
    }

    @Test
    void deletar_400() throws Exception {
        mockMvc.perform(get(BASE_URL+"/a"))
                .andExpect(status().isBadRequest());
    }
}