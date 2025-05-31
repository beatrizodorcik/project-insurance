package com.agibank.insurance.repository;

import com.agibank.insurance.model.Insurance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InsuranceRepository extends MongoRepository<Insurance, String> {
}