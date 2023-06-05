package com.hits.sport.service;

import com.hits.sport.dto.*;

import java.util.List;
import java.util.UUID;

public interface TrainerService {
    PaginationAnswerDto<ShortTrainerDto> getTrainer(String shortName, PaginationQueryDto paginationQueryDto);

    void addToTrainer(UUID trainerId);

    PaginationAnswerDto<AddToTrainerUserDto> getMyQuery(String name, PaginationQueryDto paginationQueryDto);

    void acceptQuery(UUID queryId);

    void rejectQuery(UUID queryId);
}
