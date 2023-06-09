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
    @PostMapping("/trainings/")
    public PaginationAnswerDto<ShortTrainingDto> getTraining(@Valid @RequestBody QueryTrainingDto queryTrainingDto) {
        return trainingService.getTraining(queryTrainingDto, queryTrainingDto.getPaginationQueryDto());
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
        trainingService.editTraining(trainingId, editTrainingDto);
    }

    @PostMapping(TRAINING_APPOINT)
    public void appointTraining(@Valid @RequestBody AppointingTrainingDto appointingTrainingDto) {
        trainingService.appointTraining(appointingTrainingDto);
    }

    @GetMapping(TRAINING_GET_APPOINTED)
    public List<ShortAppointedTrainingDto> getMyAppointedTrainings() {
        return trainingService.getMyAppointedTrainings();
    }
    @GetMapping(TRAINING_GET_APPOINTED + "my-training/")
    public List<ShortAppointedTrainingDto> getMyAppointingTrainings() {
        return trainingService.getMyAppointingTrainings();
    }

    @GetMapping(TRAINING_GET_THIS_APPOINTED)
    public FullAppointTrainingDto getAppointTraining(@PathVariable UUID id) {
        return trainingService.getAppointTraining(id);
    }

}
