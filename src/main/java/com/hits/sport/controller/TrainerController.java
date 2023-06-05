package com.hits.sport.controller;

import com.hits.sport.dto.AddToTrainerUserDto;
import com.hits.sport.dto.ShortTrainerDto;
import com.hits.sport.service.TrainerService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.hits.sport.utils.Path.*;

@Api
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class TrainerController {
    private final TrainerService trainerService;
    @GetMapping(TRAINER_GET)
    public List<ShortTrainerDto> getTrainer(String shortName) {
        return trainerService.getTrainer(shortName);
    }

    @PostMapping(TRAINER_ADD_QUERY)
    public void addToTrainer(UUID trainerId) {
        trainerService.addToTrainer(trainerId);
    }

    @GetMapping(TRAINER_MY_QUERY)
    public List<AddToTrainerUserDto> getMyQuery(String name) {
        return trainerService.getMyQuery(name);
    }

    @PutMapping(TRAINER_QUERY_ACCEPT)
    public void acceptQuery(UUID queryId) {
        trainerService.acceptQuery(queryId);
    }

    @DeleteMapping(TRAINER_QUERY_REJECT)
    public void rejectQuery(UUID queryId) {
        trainerService.rejectQuery(queryId);
    }
}
