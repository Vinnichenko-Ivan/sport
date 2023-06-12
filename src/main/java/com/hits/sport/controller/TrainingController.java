package com.hits.sport.controller;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.exercise.FullExerciseDto;
import com.hits.sport.dto.training.*;
import com.hits.sport.exception.NotImplementedException;
import com.hits.sport.service.TrainingService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.hits.sport.utils.Path.*;

@Api
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class TrainingController {
    private final TrainingService trainingService;
    @GetMapping(TRAINING_URL)
    public PaginationAnswerDto<ShortTrainingDto> getTraining(QueryTrainingDto queryTrainingDto, PaginationQueryDto paginationQueryDto) {
        return trainingService.getTraining(queryTrainingDto, paginationQueryDto);
    }

    @GetMapping(TRAINING_GET)
    public FullTrainingDto getTraining(@PathVariable UUID trainingId) {
        return trainingService.getTraining(trainingId);
    }

    @PostMapping(TRAINING_URL)
    public void createTraining(@Valid @RequestBody TrainingCreateDto trainingCreateDto) {
        trainingService.createTraining(trainingCreateDto);
    }

    @PutMapping(TRAINING_PUT)
    public void editTraining(@PathVariable UUID trainingId, EditTrainingDto editTrainingDto) {
        trainingService.editTraining(UUID.randomUUID(), editTrainingDto);
    }

    @PostMapping(TRAINING_APPOINT)
    public void appointTraining(@PathVariable UUID trainingId, AppointingTrainingDto appointingTrainingDto) {
        trainingService.appointTraining(trainingId, appointingTrainingDto);
    }

    @GetMapping(TRAINING_GET_APPOINTED)
    public List<ShortAppointedTrainingDto> getMyAppointedTrainings() {
        return trainingService.getMyAppointedTrainings();
    }

    @GetMapping(TRAINING_GET_THIS_APPOINTED)
    public FullAppointTrainingDto getAppointTraining(@PathVariable UUID id) {
        return trainingService.getAppointTraining(id);
    }
}
