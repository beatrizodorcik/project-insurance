package com.agibank.client.service;

import com.agibank.client.exception.ClientNotFoundException;
import com.agibank.client.model.Client;
import com.agibank.client.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientValidationService validationService;

    public ClientServiceImpl(ClientRepository repository, ClientValidationService validationService) {
        this.repository = repository;
        this.validationService = validationService;
    }

    @Override
    public Client create(Client client) {
        log.info("Creating new client with CPF: {}", client.getCpf());
        validationService.validateClient(client);
        
        if (repository.existsByCpf(client.getCpf())) {
            throw new IllegalArgumentException("Client with CPF " + client.getCpf() + " already exists");
        }
        
        return repository.save(client);
    }

    @Override
    public Optional<Client> getByCpf(String cpf) {
        log.info("Fetching client with CPF: {}", cpf);
        return repository.findByCpf(cpf);
    }

    @Override
    public Optional<Client> update(String cpf, Client client) {
        log.info("Updating client with CPF: {}", cpf);
        return repository.findByCpf(cpf)
                .map(existingClient -> {
                    client.setId(existingClient.getId());
                    validationService.validateClient(client);
                    return repository.save(client);
                });
    }

    @Override
    public void delete(String cpf) {
        log.info("Deleting client with CPF: {}", cpf);
        Client client = repository.findByCpf(cpf)
                .orElseThrow(() -> new ClientNotFoundException(cpf));
        repository.delete(client);
    }

    @Override
    public List<Client> listAll() {
        log.info("Fetching all clients");
        return repository.findAll();
    }
} 