package com.hits.sport.service.impl;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.exercise.FullExerciseDto;
import com.hits.sport.dto.exercise.GetExerciseDto;
import com.hits.sport.dto.exercise.ShortExerciseDto;
import com.hits.sport.exception.ForbiddenException;
import com.hits.sport.exception.NotFoundException;
import com.hits.sport.mapper.ExerciseMapper;
import com.hits.sport.model.CreateExerciseDto;
import com.hits.sport.model.Exercise;
import com.hits.sport.model.Trainer;
import com.hits.sport.model.User;
import com.hits.sport.repository.ExerciseRepository;
import com.hits.sport.repository.UserRepository;
import com.hits.sport.repository.specification.ExerciseSpecification;
import com.hits.sport.service.ExerciseService;
import com.hits.sport.utils.JwtProvider;
import com.hits.sport.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final ExerciseMapper exerciseMapper;
    private final ExerciseRepository exerciseRepository;
    @Override
    public PaginationAnswerDto<ShortExerciseDto> getExercise(GetExerciseDto getExerciseDto, PaginationQueryDto paginationQueryDto) {
        Page<Exercise> page = exerciseRepository.findAll(new ExerciseSpecification(getExerciseDto, jwtProvider.getUser().getTrainer()), Utils.toPageable(paginationQueryDto));
        PaginationAnswerDto<ShortExerciseDto> dto = Utils.toAnswer(page, exerciseMapper::mapToShort);
        return dto;
    }

    @Override
    public FullExerciseDto getExercise(UUID exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new NotFoundException("exercise not found"));
        FullExerciseDto dto = exerciseMapper.map(exercise); // TODO проверка доступа
        dto.setAllowedTrainerId(exercise.getAllowedTrainer().stream().map(User::getId).collect(Collectors.toSet()));
        return dto;
    }

    @Override
    public void createExercise(CreateExerciseDto createExerciseDto) {
        User user = jwtProvider.getUser();
        Trainer trainer = user.getTrainer();
        checkTrainer(trainer);
        Exercise exercise = exerciseMapper.map(createExerciseDto);
        exercise.setTrainer(trainer);
        exercise.setAllowedTrainer(new HashSet<>());
        exerciseRepository.save(exercise);
    }

    @Override
    public void checkTrainer(Trainer trainer) {
        if(trainer == null) {
            throw new ForbiddenException("not trainer");
        }
    }



}
