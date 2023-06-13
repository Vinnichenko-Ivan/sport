package com.hits.sport.dto.training;

import com.hits.sport.dto.complex.ComplexEditForTemplateDto;
import com.hits.sport.dto.complex.EditedComplexCreateDto;
import com.hits.sport.dto.exercise.ExerciseForTemplateDto;
import lombok.Data;

import java.util.List;

@Data
public class TrainingCreateDto {
    private String name;

    private String description;

    private Boolean published;

    private Boolean common;

    private List<ComplexEditForTemplateDto> complexes;
    private List<ExerciseForTemplateDto> exercises;
    private Boolean template;
}
