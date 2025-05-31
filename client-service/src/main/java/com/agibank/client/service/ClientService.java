package com.agibank.client.service;

import com.agibank.client.model.Client;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client create(Client client);

    Optional<Client> getByCpf(String cpf);

    Optional<Client> update(String cpf, Client client);

    void delete(String cpf);
    
    List<Client> listAll();
}