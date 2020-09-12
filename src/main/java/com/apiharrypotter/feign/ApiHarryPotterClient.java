package com.apiharrypotter.feign;

import com.apiharrypotter.dto.HouseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url = "https://www.potterapi.com/v1", name = "api-hp")
public interface ApiHarryPotterClient {

    @GetMapping(value = "/houses/{id}?key=$2a$10$C5GdlB8wLGeKvp1y3W3zZeWSdB7PVYT8mVMXzH9rDD5FYlrsBowHu", produces = "application/json")
    List<HouseDto> findHouse(@PathVariable("id") String id);

}
