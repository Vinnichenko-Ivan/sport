package com.hits.sport.dto.complex;

import com.hits.sport.dto.exercise.EditedExerciseAnswer;
import com.hits.sport.model.ExerciseValues;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class EditedComplexAnswer {
    private UUID complexId;
    private List<EditedExerciseAnswer> exerciseValues;
    private Integer orderNumber;
}
