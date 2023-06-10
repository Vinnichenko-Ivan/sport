package com.hits.sport.service;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.trainer.AddToTrainerUserDto;
import com.hits.sport.dto.trainer.ShortTrainerDto;
import com.hits.sport.dto.user.ShortUserDto;

import java.util.UUID;

public interface TrainerService {
    PaginationAnswerDto<ShortTrainerDto> getTrainer(String shortName, PaginationQueryDto paginationQueryDto);

    void addToTrainer(UUID trainerId);

    PaginationAnswerDto<AddToTrainerUserDto> getMyQuery(String name, PaginationQueryDto paginationQueryDto);

    void acceptQuery(UUID queryId);

    void rejectQuery(UUID queryId);

    PaginationAnswerDto<ShortUserDto> getMyUsers(String name, PaginationQueryDto paginationQueryDto);
}
