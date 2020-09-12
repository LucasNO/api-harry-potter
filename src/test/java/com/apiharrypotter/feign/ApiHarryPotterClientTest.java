package com.apiharrypotter.feign;

import com.apiharrypotter.dto.HouseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApiHarryPotterClientTest {

    @Autowired
    ApiHarryPotterClient client;

    @Test
    void findHouse() {
        HouseDto dto = HouseDto.builder()
                ._id("5a05e2b252f721a3cf2ea33f")
                .headOfHouse("Minerva McGonagall")
                .name("Gryffindor")
                .school("Hogwarts School of Witchcraft and Wizardry")
                .build();
        List<HouseDto> list = new ArrayList<>();
        list.add(dto);
        assertThat(this.client.findHouse("5a05e2b252f721a3cf2ea33f")).isEqualTo(list);
    }
}