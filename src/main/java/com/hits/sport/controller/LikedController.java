package com.hits.sport.controller;

import com.hits.sport.exception.NotImplementedException;
import com.hits.sport.service.LikedService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class LikedController {
    private final LikedService likedService;
    @GetMapping("liked/exercise/{id}")
    public Boolean checkExercise(@PathVariable UUID id) {
        return likedService.checkExercise(id);
    }
    @GetMapping("liked/complex/{id}")
    public Boolean checkComplex(@PathVariable UUID id) {
        return likedService.checkComplex(id);
    }
    @GetMapping("liked/training/{id}")
    public Boolean checkTraining(@PathVariable UUID id) {
        return likedService.checkTraining(id);
    }

    @PostMapping("liked/exercise/{id}")
    public void likeExercise(@PathVariable UUID id) {
        likedService.likeExercise(id);
    }
    @PostMapping("liked/complex/{id}")
    public void likeComplex(@PathVariable UUID id) {
        likedService.likeComplex(id);
    }
    @PostMapping("liked/training/{id}")
    public void likeTraining(@PathVariable UUID id) {
        likedService.likeTraining(id);
    }

    @DeleteMapping("liked/exercise/{id}")
    public void dislikeExercise(@PathVariable UUID id) {
        likedService.dislikeExercise(id);
    }
    @DeleteMapping("liked/complex/{id}")
    public void dislikeComplex(@PathVariable UUID id) {
        likedService.dislikeComplex(id);
    }
    @DeleteMapping("liked/training/{id}")
    public void dislikeTraining(@PathVariable UUID id) {
        likedService.dislikeTraining(id);
    }
}
