package com.hits.sport.repository;

import com.hits.sport.model.template.TrainingTemplate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TrainingRepository extends CrudRepository<TrainingTemplate, UUID> , JpaSpecificationExecutor {
}
