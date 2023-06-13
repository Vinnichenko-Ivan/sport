package com.hits.sport.service;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.training.*;


import java.util.List;
import java.util.UUID;


public interface TrainingService {
    PaginationAnswerDto<ShortTrainingDto> getTraining(QueryTrainingDto queryTrainingDto, PaginationQueryDto paginationQueryDto);

    FullTrainingDto getTraining( UUID trainingId);

    void createTraining(TrainingCreateDto trainingCreateDto);

    void editTraining(UUID trainingId, EditTrainingDto editTrainingDto);
    void appointTraining(AppointingTrainingDto appointingTrainingDto);

    List<ShortAppointedTrainingDto> getMyAppointedTrainings();

    FullAppointTrainingDto getAppointTraining(UUID id);

    List<ShortAppointedTrainingDto> getMyAppointingTrainings();
}
