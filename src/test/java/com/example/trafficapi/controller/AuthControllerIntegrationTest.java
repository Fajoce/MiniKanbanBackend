package com.example.trafficapi.controller;

import com.example.trafficapi.model.User;
import com.example.trafficapi.dto.LoginRequest;
import com.example.trafficapi.dto.RegisterUser;
import com.example.trafficapi.repository.UserRepository;
import com.example.trafficapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void cleanup() {
        userRepo.deleteAll();
    }

    @Test
    public void testRegisterAndLogin() throws Exception {
        // Registro
        RegisterUser registerUser = new RegisterUser();
        registerUser.setUsername("testuser");
        registerUser.setPassword("testpass");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.password").doesNotExist()); // si no quieres exponer

        // Verificar que se guardó en DB
        User user = userRepo.findByUsername("testuser").orElseThrow();
        assert(encoder.matches("testpass", user.getPassword()));

        // Login correcto
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpass");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("token"))); // verifica que retorna token


        loginRequest.setPassword("wrongpass");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Credenciales erróneas"));
    }
}