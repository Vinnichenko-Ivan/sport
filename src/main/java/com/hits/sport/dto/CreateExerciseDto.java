package com.hits.sport.dto;

import com.hits.sport.model.ExerciseValues;
import com.hits.sport.model.MuscleGroup;
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
