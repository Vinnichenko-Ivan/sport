package com.hits.sport.service;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.exercise.FullExerciseDto;
import com.hits.sport.dto.exercise.GetExerciseDto;
import com.hits.sport.dto.exercise.ShortExerciseDto;
import com.hits.sport.dto.trainer.ShortTrainerDto;
import com.hits.sport.dto.training.*;
import com.hits.sport.model.CreateExerciseDto;
import com.hits.sport.model.Trainer;


import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


public interface TrainingService {
    PaginationAnswerDto<ShortTrainingDto> getTraining(QueryTrainingDto queryTrainingDto, PaginationQueryDto paginationQueryDto);

    FullTrainingDto getTraining( UUID trainingId);

    void createTraining(TrainingCreateDto trainingCreateDto);

    void editTraining(UUID trainingId, EditTrainingDto editTrainingDto);
    void appointTraining(UUID trainingId, AppointingTrainingDto appointingTrainingDto);

    List<ShortAppointedTrainingDto> getMyAppointedTrainings();

    FullAppointTrainingDto getAppointTraining(UUID id);

}
