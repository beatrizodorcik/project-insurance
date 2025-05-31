package com.agibank.client.service;

import com.agibank.client.model.Client;
import com.agibank.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceTest {

    @Mock
    private ClientRepository repository;

    @Mock
    private ClientValidationService validationService;

    @InjectMocks
    private ClientServiceImpl service;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new Client();
        client.setId("1");
        client.setCpf("12345678901");
        client.setName("João Silva");
        client.setBirthDate(LocalDate.of(1990, 1, 1));
        client.setPhone("+5511999999999");
        client.setAddress("Rua das Flores, 123");
    }

    @Test
    void testCreateClientSuccess() {
        when(repository.save(client)).thenReturn(client);
        Client created = service.create(client);
        assertEquals(client, created);
        verify(repository).save(client);
        verify(validationService).validateClient(client);
    }

    @Test
    void testGetByCpfFound() {
        when(repository.findByCpf(client.getCpf())).thenReturn(Optional.of(client));
        Optional<Client> found = service.getByCpf(client.getCpf());
        assertTrue(found.isPresent());
        assertEquals(client, found.get());
    }

    @Test
    void testGetByCpfNotFound() {
        when(repository.findByCpf(client.getCpf())).thenReturn(Optional.empty());
        Optional<Client> found = service.getByCpf(client.getCpf());
        assertFalse(found.isPresent());
    }

    @Test
    void testUpdateClientSuccess() {
        Client updated = new Client();
        updated.setName("Novo Nome");
        updated.setPhone("+5511988888888");
        updated.setAddress("Nova Rua, 456");
        updated.setBirthDate(LocalDate.of(1995, 5, 5));
        when(repository.findByCpf(client.getCpf())).thenReturn(Optional.of(client));
        when(repository.save(any(Client.class))).thenAnswer(i -> i.getArgument(0));
        Optional<Client> result = service.update(client.getCpf(), updated);
        assertTrue(result.isPresent());
        assertEquals("Novo Nome", result.get().getName());
        assertEquals("+5511988888888", result.get().getPhone());
        assertEquals("Nova Rua, 456", result.get().getAddress());
        assertEquals(LocalDate.of(1995, 5, 5), result.get().getBirthDate());
        verify(validationService).validateClient(any(Client.class));
    }

    @Test
    void testUpdateClientNotFound() {
        when(repository.findByCpf(client.getCpf())).thenReturn(Optional.empty());
        Optional<Client> result = service.update(client.getCpf(), client);
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteClient() {
        when(repository.findByCpf(client.getCpf())).thenReturn(Optional.of(client));
        service.delete(client.getCpf());
        verify(repository).delete(client);
    }

    @Test
    void testListAllClients() {
        when(repository.findAll()).thenReturn(Arrays.asList(client));
        List<Client> clients = service.listAll();
        assertEquals(1, clients.size());
        assertEquals(client, clients.get(0));
    }
}