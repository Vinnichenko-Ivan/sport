package com.hits.sport.dto.complex;

import com.hits.sport.model.ComplexType;
import lombok.Data;

import java.util.List;

@Data
public class ComplexCreateDto {
    private String name;
    private String description;
    private Boolean published;
    private ComplexType complexType;
    private Integer repetitions;
    private Integer spaceDuration;
    private List<SetExerciseDto> exercises;
}
