package com.hits.sport.service;

import com.hits.sport.dto.*;
import com.hits.sport.model.CreateExerciseDto;

import java.util.UUID;

public interface ExerciseService {
    PaginationAnswerDto<ShortExerciseDto> getExercise(GetExerciseDto getExerciseDto, PaginationQueryDto paginationQueryDto);

    FullExerciseDto getExercise(UUID exerciseId);

    void createExercise(CreateExerciseDto createExerciseDto);
}
