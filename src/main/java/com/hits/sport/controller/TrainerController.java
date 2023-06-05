package com.hits.sport.controller;

import com.hits.sport.dto.AddToTrainerUserDto;
import com.hits.sport.dto.ShortTrainerDto;
import com.hits.sport.service.TrainerService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Api
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class TrainerController {
    private final TrainerService trainerService;
    public List<ShortTrainerDto> getTrainer(String shortName) {
        return trainerService.getTrainer(shortName);
    }

    public void addToTrainer(UUID trainerId) {
        trainerService.addToTrainer(trainerId);
    }

    public List<AddToTrainerUserDto> getMyQuery(String name) {
        return trainerService.getMyQuery(name);
    }

    public void acceptQuery(UUID queryId) {
        trainerService.acceptQuery(queryId);
    }

    public void rejectQuery(UUID queryId) {
        trainerService.rejectQuery(queryId);
    }
}
