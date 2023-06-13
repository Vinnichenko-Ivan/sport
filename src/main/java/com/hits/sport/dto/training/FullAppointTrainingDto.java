package com.hits.sport.dto.training;

import com.hits.sport.dto.complex.EditedComplexAnswer;
import com.hits.sport.dto.exercise.EditedExerciseAnswer;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class FullAppointTrainingDto {
    private UUID id;
    private String name;
    private String description;
    private List<EditedExerciseAnswer> exercises;
    private List<EditedComplexAnswer> complexes;
    private Set<Date> dates;
    private String trainerName;
}
