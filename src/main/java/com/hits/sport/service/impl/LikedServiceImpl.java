package com.hits.sport.service.impl;

import com.hits.sport.exception.ForbiddenException;
import com.hits.sport.exception.NotFoundException;
import com.hits.sport.model.Trainer;
import com.hits.sport.model.template.ComplexTemplate;
import com.hits.sport.model.template.ExerciseTemplate;
import com.hits.sport.model.template.TrainingTemplate;
import com.hits.sport.repository.ComplexRepository;
import com.hits.sport.repository.ExerciseTemplateRepository;
import com.hits.sport.repository.TrainingRepository;
import com.hits.sport.service.LikedService;
import com.hits.sport.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikedServiceImpl implements LikedService {
    private final JwtProvider jwtProvider;
    private final ExerciseTemplateRepository exerciseTemplateRepository;
    private final ComplexRepository complexRepository;
    private final TrainingRepository trainingRepository;

    @Override
    public Boolean checkExercise(UUID id) {
        Trainer trainer = getTrainer();
        ExerciseTemplate exercise = exerciseTemplateRepository.findById(id).orElseThrow(NotFoundException::new);
//        for(var test:exercise.getLiked()) {
//            if(test.getId().equals(trainer.getId())) {
//                return true;
//            }
//        }
//        return false;
        return exercise.getLiked().contains(trainer);
    }

    @Override
    public Boolean checkComplex(UUID id) {
        Trainer trainer = getTrainer();
        ComplexTemplate complex = complexRepository.findById(id).orElseThrow(NotFoundException::new);
//        for(var test:complex.getLiked()) {
//            if(test.getId().equals(trainer.getId())) {
//                return true;
//            }
//        }
//        return false;
        return complex.getLiked().contains(trainer);
    }

    @Override
    public Boolean checkTraining(UUID id) {
        Trainer trainer = getTrainer();
        TrainingTemplate training = trainingRepository.findById(id).orElseThrow(NotFoundException::new);
//        for(var test:training.getLiked()) {
//            if(test.getId().equals(trainer.getId())) {
//                return true;
//            }
//        }
//        return false;
        return training.getLiked().contains(trainer);
    }

    @Override
    public void likeExercise(UUID id) {
        Trainer trainer = getTrainer();
        ExerciseTemplate exercise = exerciseTemplateRepository.findById(id).orElseThrow(NotFoundException::new);
        if(!checkExercise(id)) {
            exercise.getLiked().add(trainer);
            exerciseTemplateRepository.save(exercise);
        }
    }

    @Override
    public void likeComplex(UUID id) {
        Trainer trainer = getTrainer();
        ComplexTemplate complex = complexRepository.findById(id).orElseThrow(NotFoundException::new);
        if(!checkComplex(id)) {
            complex.getLiked().add(trainer);
            complexRepository.save(complex);
        }
    }

    @Override
    public void likeTraining(UUID id) {
        Trainer trainer = getTrainer();
        TrainingTemplate training = trainingRepository.findById(id).orElseThrow(NotFoundException::new);
        if(!checkTraining(id)) {
            training.getLiked().add(trainer);
            trainingRepository.save(training);
        }
    }

    @Override
    public void dislikeExercise(UUID id) {
        Trainer trainer = getTrainer();
        ExerciseTemplate exercise = exerciseTemplateRepository.findById(id).orElseThrow(NotFoundException::new);
        if(checkExercise(id)) {
            exercise.getLiked().remove(trainer);
            exerciseTemplateRepository.save(exercise);
        }
    }

    @Override
    public void dislikeComplex(UUID id) {
        Trainer trainer = getTrainer();
        ComplexTemplate complex = complexRepository.findById(id).orElseThrow(NotFoundException::new);
        if(checkComplex(id)) {
            complex.getLiked().remove(trainer);
            complexRepository.save(complex);
        }
    }

    @Override
    public void dislikeTraining(UUID id) {
        Trainer trainer = getTrainer();
        TrainingTemplate training = trainingRepository.findById(id).orElseThrow(NotFoundException::new);
        if(checkTraining(id)) {
            training.getLiked().remove(trainer);
            trainingRepository.save(training);
        }
    }

    private Trainer getTrainer() {
        Trainer trainer = jwtProvider.getUser().getTrainer();
        if(trainer == null) {
            throw new ForbiddenException();
        }
        return trainer;
    }
}
