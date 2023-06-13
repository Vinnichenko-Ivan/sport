package com.hits.sport.dto.exercise;

import com.hits.sport.model.ExerciseValues;
import com.hits.sport.model.MuscleGroup;
import com.hits.sport.model.Trainer;
import com.hits.sport.model.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
public class FullExerciseDto {
    private String name;

    private String description;

    private Boolean published;

    private Boolean common;

    private Set<MuscleGroup> muscleGroups;

    private UUID trainerId;

    private ExerciseValues defaultValues;

    private UUID imageId;
}
