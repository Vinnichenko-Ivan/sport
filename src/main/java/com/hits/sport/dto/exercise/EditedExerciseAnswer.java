package com.hits.sport.dto.exercise;

import com.hits.sport.model.ExerciseValues;
import com.hits.sport.model.template.ExerciseTemplate;
import lombok.Data;

import javax.persistence.Embedded;
import java.util.UUID;

@Data
public class EditedExerciseAnswer {
    private UUID exerciseId;
    private ExerciseValues exerciseValues;
    private Integer orderNumber;
}
