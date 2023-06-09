package com.hits.sport.dto;

import com.hits.sport.model.MuscleGroup;
import lombok.Data;

import java.util.UUID;

@Data
public class ShortExerciseDto {
    private UUID id;
    private String name;
    private MuscleGroup muscleGroup;
    private UUID imageId;
}
