package com.hits.sport.dto.complex;

import com.hits.sport.dto.exercise.EditedExerciseAnswer;
import com.hits.sport.model.ComplexType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class FullComplexDto {
    private UUID id;
    private String name;
    private String description;
    private Boolean published;
    private Boolean common;
    private ComplexType complexType;
    private Integer repetitions;
    private Integer spaceDuration;
    private Boolean template;
    private List<EditedExerciseAnswer> exercises;
}
