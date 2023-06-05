package com.hits.sport.controller;

import com.hits.sport.dto.AddToTrainerUserDto;
import com.hits.sport.dto.PaginationAnswerDto;
import com.hits.sport.dto.PaginationQueryDto;
import com.hits.sport.dto.ShortTrainerDto;
import com.hits.sport.service.TrainerService;
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
public class TrainerController {
    private final TrainerService trainerService;
    @PostMapping(TRAINER_GET)
    public PaginationAnswerDto<ShortTrainerDto> getTrainer(String shortName, @Valid @RequestBody PaginationQueryDto paginationQueryDto) {
        return trainerService.getTrainer(shortName, paginationQueryDto);
    }

    @PostMapping(TRAINER_ADD_QUERY)
    public void addToTrainer(@PathVariable UUID trainerId) {
        trainerService.addToTrainer(trainerId);
    }

    @PostMapping(TRAINER_MY_QUERY)
    public PaginationAnswerDto<AddToTrainerUserDto> getMyQuery(String name, @Valid @RequestBody PaginationQueryDto paginationQueryDto) {
        return trainerService.getMyQuery(name, paginationQueryDto);
    }

    @PutMapping(TRAINER_QUERY_ACCEPT)
    public void acceptQuery(@PathVariable UUID queryId) {
        trainerService.acceptQuery(queryId);
    }

    @DeleteMapping(TRAINER_QUERY_REJECT)
    public void rejectQuery(@PathVariable UUID queryId) {
        trainerService.rejectQuery(queryId);
    }
}
