package com.hits.sport.dto.complex;

import com.hits.sport.model.ExerciseValues;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class EditedComplexCreateDto {
    private UUID complexId;
    private List<ExerciseValues> exerciseValuesList;
}
