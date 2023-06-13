package com.hits.sport.dto.complex;

import com.hits.sport.dto.exercise.ExerciseForTemplateDto;
import com.hits.sport.model.ComplexType;
import lombok.Data;

import java.util.List;

@Data
public class ComplexEditForTemplateDto {
    private List<ExerciseForTemplateDto> exercises;
    private Integer orderNumber;
    private ComplexType complexType;
    private Integer repetitions;
    private Integer spaceDuration;
}
