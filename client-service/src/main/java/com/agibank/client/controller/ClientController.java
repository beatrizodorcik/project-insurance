package com.agibank.client.controller;

import com.agibank.client.model.Client;
import com.agibank.client.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody Client client) {
        log.info("Received request to create client with CPF: {}", client.getCpf());
        return ResponseEntity.ok(service.create(client));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Client> getByCpf(@PathVariable String cpf) {
        log.info("Received request to get client with CPF: {}", cpf);
        return service.getByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Client> update(@PathVariable String cpf, @RequestBody Client client) {
        log.info("Received request to update client with CPF: {}", cpf);
        return service.update(cpf, client)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable String cpf) {
        log.info("Received request to delete client with CPF: {}", cpf);
        service.delete(cpf);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Client>> listAll() {
        log.info("Received request to list all clients");
        return ResponseEntity.ok(service.listAll());
    }
}