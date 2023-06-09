package com.hits.sport.service.impl;

import com.hits.sport.dto.*;
import com.hits.sport.exception.NotImplementedException;
import com.hits.sport.model.CreateExerciseDto;
import com.hits.sport.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    @Override
    public PaginationAnswerDto<ShortExerciseDto> getExercise(GetExerciseDto getExerciseDto, PaginationQueryDto paginationQueryDto) {
        throw new NotImplementedException();
    }

    @Override
    public FullExerciseDto getExercise(UUID exerciseId) {
        throw new NotImplementedException();
    }

    @Override
    public void createExercise(CreateExerciseDto createExerciseDto) {
        throw new NotImplementedException();
    }
}
