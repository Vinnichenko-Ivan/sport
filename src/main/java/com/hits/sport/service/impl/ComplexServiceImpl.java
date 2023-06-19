package com.hits.sport.service.impl;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.complex.*;
import com.hits.sport.exception.NotFoundException;
import com.hits.sport.mapper.ComplexMapper;
import com.hits.sport.model.edited.EditedExercise;
import com.hits.sport.model.template.ComplexTemplate;
import com.hits.sport.repository.ComplexRepository;
import com.hits.sport.repository.EditedExerciseRepository;
import com.hits.sport.repository.ExerciseTemplateRepository;
import com.hits.sport.repository.specification.ComplexSpecification;
import com.hits.sport.service.ComplexService;
import com.hits.sport.service.ExerciseService;
import com.hits.sport.service.TrainingService;
import com.hits.sport.utils.JwtProvider;
import com.hits.sport.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComplexServiceImpl implements ComplexService {
    private final ComplexMapper complexMapper;
    private final TrainingService trainingService;
    private final ComplexRepository complexRepository;
    private final ExerciseTemplateRepository exerciseTemplateRepository;
    private final ExerciseService exerciseService;
    private final JwtProvider jwtProvider;
    private final EditedExerciseRepository exerciseRepository;
    @Override
    @Transactional
    public void createComplex(ComplexCreateDto complexCreateDto) {
        exerciseService.checkTrainer(jwtProvider.getUser().getTrainer());
        ComplexTemplate complexTemplate = complexMapper.map(complexCreateDto);
        complexTemplate.setTrainer(jwtProvider.getUser().getTrainer());
        complexTemplate.setEditedExercises(complexCreateDto.getExercises().stream().map(
                (setExerciseDto)->{
                    EditedExercise editedExercise = new EditedExercise();
                    editedExercise.setExercise(
                            exerciseTemplateRepository.findById(setExerciseDto.getExerciseId()).orElseThrow(()->new NotFoundException("not found" +setExerciseDto.getExerciseId().toString()))
                    );
                    editedExercise.setOrderNumber(complexCreateDto.getExercises().indexOf(setExerciseDto) + 1);
                    editedExercise.setExerciseValues(setExerciseDto.getExerciseValues());
                    editedExercise = exerciseRepository.save(editedExercise);
                    return editedExercise;
                }
        ).collect(Collectors.toList()));
        complexTemplate.setCommon(false);
        complexRepository.save(complexTemplate);
    }

    @Override
    public FullComplexDto getComplex(UUID complexId) {
        exerciseService.checkTrainer(jwtProvider.getUser().getTrainer());
        ComplexTemplate complexTemplate = complexRepository.findById(complexId).orElseThrow(() -> new NotFoundException("complex not found"));
        var temp = complexMapper.map(complexTemplate);
        temp.setExercises(complexTemplate.getEditedExercises().stream().map(trainingService::map).collect(Collectors.toList()));
        return temp;
        //throw new NotImplementedException();// TODO проверка доступа
    }

    @Override
    public PaginationAnswerDto<ShortComplexDto> getComplexes(QueryComplexDto queryComplexDto, PaginationQueryDto paginationQueryDto) {
        exerciseService.checkTrainer(jwtProvider.getUser().getTrainer());
        Page<ComplexTemplate> page = complexRepository.findAll(new ComplexSpecification(queryComplexDto), Utils.toPageable(paginationQueryDto));
        PaginationAnswerDto<ShortComplexDto> dto = Utils.toAnswer(page, (complex) -> {
            ShortComplexDto complexDto = complexMapper.mapToShort(complex);
            return complexDto;
        });
        return dto;
    }

    @Override
    public void editComplex(UUID complexId, EditComplexDto editComplexDto) {
        exerciseService.checkTrainer(jwtProvider.getUser().getTrainer());
        ComplexTemplate complexTemplate = complexRepository.findById(complexId).orElseThrow(() -> new NotFoundException("complex not found"));
        complexMapper.map(complexTemplate,editComplexDto);
        complexRepository.save(complexTemplate);
    }
}

