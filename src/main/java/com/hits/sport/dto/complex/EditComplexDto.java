package com.hits.sport.dto.complex;

import com.hits.sport.model.ComplexType;
import lombok.Data;

import java.util.List;

@Data
public class EditComplexDto {
    private String name;
    private String description;
    private Boolean published;
    private Boolean common;
    private ComplexType complexType;
    private Integer repetitions;
    private Integer spaceDuration;
    private Boolean template;
    private List<SetExerciseDto> exercises;
}
