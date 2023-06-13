package com.hits.sport.model;

import com.hits.sport.model.template.ExerciseTemplate;

import java.util.List;
import java.util.UUID;

public interface Doing {
    UUID getId();
    List<ExerciseTemplate> getExercises();
    Integer getOrderNumber();
}
