package com.hits.sport.controller;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.exercise.FullExerciseDto;
import com.hits.sport.dto.exercise.GetExerciseDto;
import com.hits.sport.dto.exercise.ShortExerciseDto;
import com.hits.sport.model.CreateExerciseDto;
import com.hits.sport.service.ExerciseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.hits.sport.utils.Path.*;

@Api
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class ExerciseController {
    private final ExerciseService exerciseService;
    @PostMapping(EXERCISES_URL)
    public PaginationAnswerDto<ShortExerciseDto> getExercise(@Valid @RequestBody GetExerciseDto getExerciseDto, @Valid @RequestBody PaginationQueryDto paginationQueryDto) {
        return exerciseService.getExercise(getExerciseDto, paginationQueryDto);
    }

    @GetMapping(EXERCISE_GET)
    public FullExerciseDto getExercise(@PathVariable UUID exerciseId) {
        return exerciseService.getExercise(exerciseId);
    }

    @PostMapping(EXERCISE_URL)
    public void createExercise(@Valid @RequestBody CreateExerciseDto createExerciseDto) {
        exerciseService.createExercise(createExerciseDto);
    }
}
