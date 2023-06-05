package com.hits.sport.repository;

import com.hits.sport.model.Trainer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrainerRepository extends CrudRepository<Trainer, UUID>, JpaSpecificationExecutor {
}
