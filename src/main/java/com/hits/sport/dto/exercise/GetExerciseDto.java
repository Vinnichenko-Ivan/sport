package com.hits.sport.dto.exercise;

import com.hits.sport.dto.common.PaginationQueryDto;
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
    private PaginationQueryDto paginationQueryDto;
}
