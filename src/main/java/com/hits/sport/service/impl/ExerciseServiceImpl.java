package com.hits.sport.service.impl;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.exercise.EditExerciseDto;
import com.hits.sport.dto.exercise.FullExerciseDto;
import com.hits.sport.dto.exercise.GetExerciseDto;
import com.hits.sport.dto.exercise.ShortExerciseDto;
import com.hits.sport.exception.ForbiddenException;
import com.hits.sport.exception.NotFoundException;
import com.hits.sport.mapper.ExerciseMapper;
import com.hits.sport.dto.CreateExerciseDto;
import com.hits.sport.model.template.ExerciseTemplate;
import com.hits.sport.model.Trainer;
import com.hits.sport.model.User;
import com.hits.sport.repository.ExerciseTemplateRepository;
import com.hits.sport.repository.UserRepository;
import com.hits.sport.repository.specification.ExerciseSpecification;
import com.hits.sport.service.ExerciseService;
import com.hits.sport.utils.JwtProvider;
import com.hits.sport.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final ExerciseMapper exerciseMapper;
    private final ExerciseTemplateRepository exerciseTemplateRepository;
    @Override
    public PaginationAnswerDto<ShortExerciseDto> getExercise(GetExerciseDto getExerciseDto, PaginationQueryDto paginationQueryDto) {
        Page<ExerciseTemplate> page = exerciseTemplateRepository.findAll(new ExerciseSpecification(getExerciseDto, jwtProvider.getUser().getTrainer()), Utils.toPageable(paginationQueryDto));
        PaginationAnswerDto<ShortExerciseDto> dto = Utils.toAnswer(page, exerciseMapper::mapToShort);
        return dto;
    }

    @Override
    public FullExerciseDto getExercise(UUID exerciseId) {
        ExerciseTemplate exerciseTemplate = exerciseTemplateRepository.findById(exerciseId).orElseThrow(() -> new NotFoundException("exercise not found"));
        FullExerciseDto dto = exerciseMapper.map(exerciseTemplate); // TODO проверка доступа
//        dto.setAllowedTrainerId(exercise.getAllowedTrainer().stream().map(User::getId).collect(Collectors.toSet()));

        return dto;
    }

    @Override
    public void createExercise(CreateExerciseDto createExerciseDto) {
        User user = jwtProvider.getUser();
        Trainer trainer = user.getTrainer();
        checkTrainer(trainer);
        ExerciseTemplate exerciseTemplate = exerciseMapper.map(createExerciseDto);
        exerciseTemplate.setTrainer(trainer);
        exerciseTemplateRepository.save(exerciseTemplate);
    }

    @Override
    public void checkTrainer(Trainer trainer) {
        if(trainer == null) {
            throw new ForbiddenException("not trainer");
        }
    }

    @Override
    public void editExercise(UUID exerciseId, EditExerciseDto editExerciseDto) {
        ExerciseTemplate exerciseTemplate = exerciseTemplateRepository.findById(exerciseId).orElseThrow(() -> new NotFoundException("exercise not found"));
        if(!exerciseTemplate.getTrainer().getId().equals(jwtProvider.getUser().getId())) {
            throw new ForbiddenException();
        }
        exerciseMapper.map(exerciseTemplate, editExerciseDto);
        exerciseTemplateRepository.save(exerciseTemplate);
    }
}
