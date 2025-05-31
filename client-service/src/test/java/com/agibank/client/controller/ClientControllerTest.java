package com.agibank.client.controller;

import com.agibank.client.model.Client;
import com.agibank.client.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId("1");
        client.setCpf("12345678901");
        client.setName("João Silva");
        client.setBirthDate(LocalDate.of(1990, 1, 1));
        client.setPhone("+5511999999999");
        client.setAddress("Rua das Flores, 123");
    }

    @Test
    void testCreateClient() throws Exception {
        Mockito.when(service.create(any(Client.class))).thenReturn(client);
        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(client.getCpf()));
    }

    @Test
    void testGetByCpfFound() throws Exception {
        Mockito.when(service.getByCpf(client.getCpf())).thenReturn(Optional.of(client));
        mockMvc.perform(get("/api/clients/{cpf}", client.getCpf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(client.getCpf()));
    }

    @Test
    void testGetByCpfNotFound() throws Exception {
        Mockito.when(service.getByCpf(client.getCpf())).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/clients/{cpf}", client.getCpf()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateClient() throws Exception {
        Mockito.when(service.update(eq(client.getCpf()), any(Client.class))).thenReturn(Optional.of(client));
        mockMvc.perform(put("/api/clients/{cpf}", client.getCpf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(client.getCpf()));
    }

    @Test
    void testDeleteClient() throws Exception {
        Mockito.doNothing().when(service).delete(client.getCpf());
        mockMvc.perform(delete("/api/clients/{cpf}", client.getCpf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testListAllClients() throws Exception {
        Mockito.when(service.listAll()).thenReturn(Collections.singletonList(client));
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cpf").value(client.getCpf()));
    }
} 