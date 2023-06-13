package com.hits.sport.dto.training;

import com.hits.sport.dto.complex.EditedComplexAnswer;
import com.hits.sport.dto.complex.FullSetComplexDto;
import com.hits.sport.dto.exercise.EditedExerciseAnswer;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class FullTrainingDto {
    private UUID id;
    private String name;
    private String description;
    private Boolean published;
    private Boolean common;
    private List<EditedExerciseAnswer> exercises;
    private List<EditedComplexAnswer> complexes;
}
