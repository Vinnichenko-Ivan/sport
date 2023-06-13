package com.hits.sport.service.impl;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.complex.ComplexEditForTemplateDto;
import com.hits.sport.dto.complex.EditedComplexAnswer;
import com.hits.sport.dto.exercise.EditedExerciseAnswer;
import com.hits.sport.dto.exercise.ExerciseForTemplateDto;
import com.hits.sport.dto.training.*;
import com.hits.sport.exception.BadRequestException;
import com.hits.sport.exception.NotFoundException;
import com.hits.sport.exception.NotImplementedException;
import com.hits.sport.mapper.TrainingMapper;
import com.hits.sport.model.edited.EditedComplex;
import com.hits.sport.model.edited.EditedExercise;
import com.hits.sport.model.template.ExerciseTemplate;
import com.hits.sport.model.template.TrainingTemplate;
import com.hits.sport.repository.EditedComplexRepository;
import com.hits.sport.repository.EditedExerciseRepository;
import com.hits.sport.repository.ExerciseTemplateRepository;
import com.hits.sport.repository.TrainingRepository;
import com.hits.sport.service.ExerciseService;
import com.hits.sport.service.TrainingService;
import com.hits.sport.utils.JwtProvider;
import com.hits.sport.utils.Utils;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final ExerciseService exerciseService;
    private final JwtProvider jwtProvider;
    private final EditedExerciseRepository editedExerciseRepository;
    private final EditedComplexRepository editedComplexRepository;
    private final ExerciseTemplateRepository exerciseTemplateRepository;
    @Override
    public PaginationAnswerDto<ShortTrainingDto> getTraining(QueryTrainingDto queryTrainingDto, PaginationQueryDto paginationQueryDto) {
        Page<TrainingTemplate> page = trainingRepository.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and();
            }
        }, Utils.toPageable(paginationQueryDto));

        return Utils.toAnswer(page,
                (training) -> {
                    ShortTrainingDto shortTrainingDto = new ShortTrainingDto();
                    shortTrainingDto.setId(training.getId());
                    shortTrainingDto.setDescription(training.getDescription());
                    shortTrainingDto.setName(training.getName());
                    shortTrainingDto.setCommon(training.getCommon());
                    return shortTrainingDto;
                }
        );
    }

    @Override
    public FullTrainingDto getTraining(UUID trainingId) {
        TrainingTemplate trainingTemplate = trainingRepository.findById(trainingId).orElseThrow(()->new NotFoundException());
        FullTrainingDto dto = new FullTrainingDto();
        dto.setCommon(trainingTemplate.getCommon());
        dto.setDescription(trainingTemplate.getDescription());
        dto.setPublished(trainingTemplate.getPublished());
        dto.setId(trainingTemplate.getId());
        dto.setComplexes(trainingTemplate.getEditedComplexes().stream().map((complex)->{
            EditedComplexAnswer editedComplexAnswer = new EditedComplexAnswer();
            editedComplexAnswer.setComplexId(complex.getId());
            editedComplexAnswer.setOrderNumber(editedComplexAnswer.getOrderNumber());
            editedComplexAnswer.setExerciseValues(
                    complex.getEditedExercises().stream().map(this::map).collect(Collectors.toList())
            );
            return editedComplexAnswer;
        }).collect(Collectors.toList()));
        dto.setExercises(trainingTemplate.getEditedExercises().stream().map(this::map).collect(Collectors.toList()));
        return dto;

    }

    private EditedExerciseAnswer map(EditedExercise exercise) {
        EditedExerciseAnswer dto = new EditedExerciseAnswer();
        dto.setExerciseId(exercise.getId());
        dto.setOrderNumber(exercise.getOrderNumber());
        dto.setExerciseValues(exercise.getExerciseValues());
        return dto;
    }
    @Override
    @Transactional
    public void createTraining(TrainingCreateDto trainingCreateDto) {
        exerciseService.checkTrainer(jwtProvider.getUser().getTrainer());
        TrainingTemplate trainingTemplate = trainingMapper.map(trainingCreateDto);
        trainingTemplate.setTrainer(jwtProvider.getUser().getTrainer());

        trainingTemplate.setEditedExercises(
                trainingCreateDto.getExercises().stream().map(
                    this::map
                ).collect(Collectors.toList()));
        trainingTemplate.setEditedComplexes(
                trainingCreateDto.getComplexes().stream().map(
                        this::map
                ).collect(Collectors.toList()));
        checkOrder(
                trainingTemplate.getEditedExercises().stream().map(EditedExercise::getOrderNumber).collect(Collectors.toList()),
                trainingTemplate.getEditedComplexes().stream().map(EditedComplex::getOrderNumber).collect(Collectors.toList())
        );
        trainingRepository.save(trainingTemplate);
    }

    @Override
    public void editTraining(UUID trainingId, EditTrainingDto editTrainingDto) {
        throw new NotImplementedException();
    }

    @Override
    public void appointTraining(UUID trainingId, AppointingTrainingDto appointingTrainingDto) {
        throw new NotImplementedException();
    }

    @Override
    public List<ShortAppointedTrainingDto> getMyAppointedTrainings() {
        throw new NotImplementedException();
    }

    @Override
    public FullAppointTrainingDto getAppointTraining(UUID id) {
        throw new NotImplementedException();
    }

    private EditedExercise map(ExerciseForTemplateDto exercise) {

        ExerciseTemplate exerciseTemplate = exerciseTemplateRepository.findById(exercise.getExerciseId()).orElseThrow(
                ()->new NotFoundException("not found + " + exercise.getExerciseId().toString())
        );
        EditedExercise editedExercise = new EditedExercise();
        editedExercise.setOrderNumber(exercise.getOrderNumber());
        editedExercise.setExercise(exerciseTemplate);
        editedExercise.setExerciseValues(exercise.getExerciseValues());
        editedExercise = editedExerciseRepository.save(editedExercise);
        return editedExercise;
    }

    private EditedComplex map(ComplexEditForTemplateDto complex) {
        EditedComplex editedComplex = new EditedComplex();
        editedComplex.setComplexType(complex.getComplexType());
        editedComplex.setRepetitions(complex.getRepetitions());
        editedComplex.setOrderNumber(complex.getOrderNumber());
        editedComplex.setSpaceDuration(complex.getSpaceDuration());
        editedComplex.setEditedExercises(complex.getExercises().stream().map(this::map).collect(Collectors.toList()));
        editedComplex = editedComplexRepository.save(editedComplex);
        return editedComplex;
    }

    private void checkOrder(List<Integer> complexOrder, List<Integer> exercisesOrder) {
        int counter = 1;

        ListIterator<Integer> complex = complexOrder.listIterator();
        ListIterator<Integer> exercises = exercisesOrder.listIterator();

        while (complex.hasNext() || exercises.hasNext()) {
            if(!complex.hasNext()) {
                checkCounter(counter, exercises.next());
            }
            else if(!exercises.hasNext()) {
                checkCounter(counter, complex.next());
            }
            else {
                Integer complexInt = complex.next();
                complex.previous();
                Integer exerciseInt = exercises.next();
                exercises.previous();
                if(checkCounter(counter, complexInt, exerciseInt)) {
                    complex.next();
                }
                else {
                    exercises.next();
                }
            }
            counter++;
        }
    }
    private void checkCounter(Integer counter, Integer real) {
        if(counter != real) {
            throw new BadRequestException("bad order");
        }
    }

    private boolean checkCounter(Integer counter, Integer real1, Integer real2) {
        if(counter == real1)
        {
            return true;
        }
        else if(counter == real2)
        {
            return false;
        }
        else {
            throw new BadRequestException("bad order");
        }
    }
}
