package com.hits.sport.dto;

import com.hits.sport.model.MuscleGroup;
import lombok.Data;

import java.util.Set;

@Data
public class GetExerciseDto {
    private Set<MuscleGroup> muscleGroups;
    private String name;
    private Boolean common;
    private Boolean my;
    private Boolean shared;
}
