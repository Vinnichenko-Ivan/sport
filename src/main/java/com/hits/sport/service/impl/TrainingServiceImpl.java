package com.hits.sport.service.impl;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.exercise.FullExerciseDto;
import com.hits.sport.dto.training.*;
import com.hits.sport.exception.NotImplementedException;
import com.hits.sport.mapper.TrainingMapper;
import com.hits.sport.model.Training;
import com.hits.sport.repository.TrainingRepository;
import com.hits.sport.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    @Override
    public PaginationAnswerDto<ShortTrainingDto> getTraining(QueryTrainingDto queryTrainingDto, PaginationQueryDto paginationQueryDto) {
        throw new NotImplementedException();
    }

    @Override
    public FullTrainingDto getTraining(UUID trainingId) {
        throw new NotImplementedException();
    }

    @Override
    public void createTraining(TrainingCreateDto trainingCreateDto) {

        throw new NotImplementedException();
    }

    @Override
    public void editTraining(UUID trainingId, EditTrainingDto editTrainingDto) {
        throw new NotImplementedException();
    }

    @Override
    public void appointTraining(UUID trainingId, AppointingTrainingDto appointingTrainingDto) {
        throw new NotImplementedException();
    }

    @Override
    public List<ShortAppointedTrainingDto> getMyAppointedTrainings() {
        throw new NotImplementedException();
    }

    @Override
    public FullAppointTrainingDto getAppointTraining(UUID id) {
        throw new NotImplementedException();
    }
}
