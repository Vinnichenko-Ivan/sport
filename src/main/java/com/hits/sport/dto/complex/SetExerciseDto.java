package com.hits.sport.dto.complex;

import com.hits.sport.model.ExerciseValues;
import lombok.Data;

import java.util.UUID;

@Data
public class SetExerciseDto {
    private UUID exerciseId;
    private ExerciseValues exerciseValues;
}
