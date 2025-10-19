package com.example.trafficapi.controller;

import com.example.trafficapi.dto.InfractionCreateDto;
import com.example.trafficapi.model.Infraction;
import com.example.trafficapi.model.InfractionStatus;
import com.example.trafficapi.repository.InfractionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class InfractionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InfractionRepository repo;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        repo.deleteAll();
    }

    @Test
    public void createAndGet_shouldReturnCreated_andThenOk() throws Exception {
        InfractionCreateDto dto = new InfractionCreateDto();
        dto.setDriverName("Juan Perez");
        dto.setDriverId("72189087");
        dto.setAmount(1.0);
        dto.setDueDate(LocalDateTime.now().plusDays(10));
        dto.setOfficerName("Agente 1");

        mockMvc.perform(post("/infractions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/infractions"))
                .andExpect(status().isOk());
    }
}
