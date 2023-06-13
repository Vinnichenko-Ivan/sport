package com.hits.sport.dto.exercise;

import com.hits.sport.model.ExerciseValues;
import lombok.Data;

import java.util.UUID;

@Data
public class ExerciseForTemplateDto {
    private UUID exerciseId;
    private ExerciseValues exerciseValues;
    private Integer orderNumber;
}
