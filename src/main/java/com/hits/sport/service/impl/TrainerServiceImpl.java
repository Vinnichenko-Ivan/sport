package com.hits.sport.service.impl;

import com.hits.sport.dto.AddToTrainerUserDto;
import com.hits.sport.dto.ShortTrainerDto;
import com.hits.sport.exception.NotImplementedException;
import com.hits.sport.service.TrainerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrainerServiceImpl implements TrainerService {
    @Override
    public List<ShortTrainerDto> getTrainer(String shortName) {
        throw new NotImplementedException();
    }

    @Override
    public void addToTrainer(UUID trainerId) {
        throw new NotImplementedException();
    }

    @Override
    public List<AddToTrainerUserDto> getMyQuery(String name) {
        throw new NotImplementedException();
    }

    @Override
    public void acceptQuery(UUID queryId) {
        throw new NotImplementedException();
    }

    @Override
    public void rejectQuery(UUID queryId) {
        throw new NotImplementedException();
    }
}
