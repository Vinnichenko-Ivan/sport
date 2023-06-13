package com.hits.sport.repository;

import com.hits.sport.model.template.ExerciseTemplate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ExerciseTemplateRepository extends CrudRepository<ExerciseTemplate, UUID>, JpaSpecificationExecutor {
}
