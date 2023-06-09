package com.hits.sport.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
public class CreateExerciseDto {
    private String name;

    private String description;

    private Boolean published;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<MuscleGroup> muscleGroups;

    private ExerciseValues defaultValues;

    private UUID imageId;
}
