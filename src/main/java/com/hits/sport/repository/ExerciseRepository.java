package com.hits.sport.repository;

import com.hits.sport.model.Exercise;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ExerciseRepository extends CrudRepository<Exercise, UUID>, JpaSpecificationExecutor {
}
