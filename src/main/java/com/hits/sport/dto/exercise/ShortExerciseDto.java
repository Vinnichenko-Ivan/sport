package com.hits.sport.dto.exercise;

import com.hits.sport.model.MuscleGroup;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ShortExerciseDto {
    private UUID id;
    private String name;
    private Set<MuscleGroup> muscleGroup;
    private UUID imageId;
}
