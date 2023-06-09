package com.hits.sport.service;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.exercise.EditExerciseDto;
import com.hits.sport.dto.exercise.FullExerciseDto;
import com.hits.sport.dto.exercise.GetExerciseDto;
import com.hits.sport.dto.exercise.ShortExerciseDto;
import com.hits.sport.dto.CreateExerciseDto;
import com.hits.sport.model.Trainer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

public interface ExerciseService {
    PaginationAnswerDto<ShortExerciseDto> getExercise(GetExerciseDto getExerciseDto, PaginationQueryDto paginationQueryDto);
    FullExerciseDto getExercise(UUID exerciseId);
    void createExercise(CreateExerciseDto createExerciseDto);
    void checkTrainer(Trainer trainer);
    void editExercise(UUID exerciseId, EditExerciseDto editExerciseDto);
}
