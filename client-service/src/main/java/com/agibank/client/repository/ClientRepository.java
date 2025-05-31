package com.agibank.client.repository;

import com.agibank.client.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String> {
    Optional<Client> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    void deleteByCpf(String cpf);
}