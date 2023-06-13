package com.hits.sport.repository;

import com.hits.sport.model.edited.EditedExercise;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EditedExerciseRepository extends CrudRepository<EditedExercise, UUID> {
}
