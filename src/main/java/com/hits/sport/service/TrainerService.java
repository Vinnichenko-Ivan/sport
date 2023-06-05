package com.hits.sport.service;

import com.hits.sport.dto.AddToTrainerUserDto;
import com.hits.sport.dto.ShortTrainerDto;
import com.hits.sport.dto.ShortUserDto;

import java.util.List;
import java.util.UUID;

public interface TrainerService {
    List<ShortTrainerDto> getTrainer(String shortName);

    void addToTrainer(UUID trainerId);

    List<AddToTrainerUserDto> getMyQuery(String name);

    void acceptQuery(UUID queryId);

    void rejectQuery(UUID queryId);
}
