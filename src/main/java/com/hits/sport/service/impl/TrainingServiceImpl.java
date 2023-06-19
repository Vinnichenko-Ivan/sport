package com.hits.sport.service.impl;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.complex.ComplexEditForTemplateDto;
import com.hits.sport.dto.complex.EditedComplexAnswer;
import com.hits.sport.dto.exercise.EditedExerciseAnswer;
import com.hits.sport.dto.exercise.ExerciseForTemplateDto;
import com.hits.sport.dto.training.*;
import com.hits.sport.exception.BadRequestException;
import com.hits.sport.exception.ForbiddenException;
import com.hits.sport.exception.NotFoundException;
import com.hits.sport.exception.NotImplementedException;
import com.hits.sport.mapper.TrainingMapper;
import com.hits.sport.model.Group;
import com.hits.sport.model.User;
import com.hits.sport.model.appointed.AppointedTraining;
import com.hits.sport.model.edited.EditedComplex;
import com.hits.sport.model.edited.EditedExercise;
import com.hits.sport.model.template.ExerciseTemplate;
import com.hits.sport.model.template.TrainingTemplate;
import com.hits.sport.repository.*;
import com.hits.sport.repository.specification.TrainingSpecification;
import com.hits.sport.service.ExerciseService;
import com.hits.sport.service.TrainingService;
import com.hits.sport.utils.JwtProvider;
import com.hits.sport.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;
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
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final AppointTrainingRepository appointingTrainingRepository;
    @Override
    public PaginationAnswerDto<ShortTrainingDto> getTraining(QueryTrainingDto queryTrainingDto, PaginationQueryDto paginationQueryDto) {
        Page<TrainingTemplate> page = trainingRepository.findAll(new TrainingSpecification(queryTrainingDto, jwtProvider.getUser().getTrainer()), Utils.toPageable(paginationQueryDto));

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

    @Override
    @Transactional
    public void createTraining(TrainingCreateDto trainingCreateDto) {
        exerciseService.checkTrainer(jwtProvider.getUser().getTrainer());
        TrainingTemplate trainingTemplate = trainingMapper.map(trainingCreateDto);
        trainingTemplate.setTrainer(jwtProvider.getUser().getTrainer());

        if(trainingCreateDto.getExercises() != null){
            trainingTemplate.setEditedExercises(
                    trainingCreateDto.getExercises().stream().map(
                            this::map
                    ).collect(Collectors.toList()));
        }
        if(trainingCreateDto.getComplexes() != null) {
            trainingTemplate.setEditedComplexes(
                    trainingCreateDto.getComplexes().stream().map(
                            this::map
                    ).collect(Collectors.toList()));
        }
        List<Integer> complexOrder = trainingTemplate.getEditedExercises()!=null?trainingTemplate.getEditedExercises().stream().map(EditedExercise::getOrderNumber).collect(Collectors.toList()):null;
        List<Integer> exercisesOrder = trainingTemplate.getEditedComplexes()!=null?trainingTemplate.getEditedComplexes().stream().map(EditedComplex::getOrderNumber).collect(Collectors.toList()):null;
        checkOrder(complexOrder, exercisesOrder);
        trainingRepository.save(trainingTemplate);
    }

    @Override
    public void editTraining(UUID trainingId, EditTrainingDto editTrainingDto) {
        throw new NotImplementedException();
    }

    @Override
    public void appointTraining(AppointingTrainingDto appointingTrainingDto) {
        //TODO проверка доступа]
        if(jwtProvider.getUser().getTrainer() == null) {
            throw new ForbiddenException();
        }
        List<Integer> complexOrder = appointingTrainingDto.getComplexes()!=null?appointingTrainingDto.getComplexes().stream().map(ComplexEditForTemplateDto::getOrderNumber).collect(Collectors.toList()):null;
        List<Integer> exercisesOrder = appointingTrainingDto.getExercise()!=null?appointingTrainingDto.getExercise().stream().map(ExerciseForTemplateDto::getOrderNumber).collect(Collectors.toList()):null;
        checkOrder(complexOrder, exercisesOrder);

        AppointedTraining training = new AppointedTraining();
        training.setDates(appointingTrainingDto.getDates());
        training.setTrainer(jwtProvider.getUser().getTrainer());
        training.setName(appointingTrainingDto.getName());
        training.setDescription(appointingTrainingDto.getDescription());

        Set<User> users = new HashSet<>();
        if(appointingTrainingDto.getUserIds() != null) {
            for(UUID id : appointingTrainingDto.getUserIds()) {
                User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("user not found" + id.toString()));
                users.add(user);
            }
        }

        training.setUsers(users);

        Set<Group> groups = new HashSet<>();
        if(appointingTrainingDto.getGroupIds() != null) {
            for (UUID id : appointingTrainingDto.getGroupIds()) {
                Group group = groupRepository.findById(id).orElseThrow(() -> new NotFoundException("user not found" + id.toString()));
                groups.add(group);
            }
        }
        training.setGroups(groups);
        if(appointingTrainingDto.getExercise() != null) {
            training.setEditedExercises(appointingTrainingDto.getExercise().stream().map(this::map).collect(Collectors.toList()));
        }
        if(appointingTrainingDto.getComplexes() != null) {
            training.setEditedComplexes(appointingTrainingDto.getComplexes().stream().map(this::map).collect(Collectors.toList()));
        }

        appointingTrainingRepository.save(training);
    }

    @Override
    @Transactional
    public List<ShortAppointedTrainingDto> getMyAppointedTrainings() {
        User user = jwtProvider.getUser();
        List<AppointedTraining> trainings = appointingTrainingRepository.getAll();// TODO SQL
        return trainings.stream().filter(appointedTraining -> {
            Boolean isNow = true;
            for(Date date : appointedTraining.getDates()) {
                if(date.after(new Date(System.currentTimeMillis()))) {
                    isNow = false;
                    break;
                }
            }
            if(isNow) {
                return false;
            }

            if(appointedTraining.getUsers().contains(user)) {
                return true;
            }
            for(Group group : appointedTraining.getGroups()) {
                if(group.getUsers().contains(user)) {
                    return true;
                }
            }
            return false;
        }).map(this::map).collect(Collectors.toList());
    }

    @Override
    public List<ShortAppointedTrainingDto> getMyAppointingTrainings() {

        User user = jwtProvider.getUser();
        if(user.getTrainer() == null) {
            throw new ForbiddenException("not trainer");//todo ДОСТУП
        }
        List<AppointedTraining> trainings = appointingTrainingRepository.getAll();// TODO SQL
        return trainings.stream().filter(appointedTraining -> {
            Boolean isNow = true;
            for(Date date : appointedTraining.getDates()) {
                if(date.after(new Date(System.currentTimeMillis()))) {
                    isNow = false;
                    break;
                }
            }
            if(isNow) {
                return false;
            }

            if(appointedTraining.getTrainer().equals(user.getTrainer())) {
                return true;
            }
//            for(Group group : appointedTraining.getGroups()) {//TODO отображение если тренер
//                if(group.getUsers().contains(user)) {
//                    return true;
//                }
//            }
            return false;
        }).map(this::map).collect(Collectors.toList());
    }

    @Override
    public FullAppointTrainingDto getAppointTraining(UUID id) {
        AppointedTraining appointedTraining =appointingTrainingRepository.findById(id).orElseThrow(NotFoundException::new);
        FullAppointTrainingDto dto = new FullAppointTrainingDto();
        dto.setDescription(appointedTraining.getDescription());
        dto.setId(appointedTraining.getId());
        dto.setName(appointedTraining.getName());
        dto.setTrainerName(appointedTraining.getTrainer().getUser().getName());
        dto.setDescription(appointedTraining.getDescription());
        dto.setId(appointedTraining.getId());
        dto.setComplexes(appointedTraining.getEditedComplexes().stream().map((complex)->{
            EditedComplexAnswer editedComplexAnswer = new EditedComplexAnswer();
            editedComplexAnswer.setComplexId(complex.getId());
            editedComplexAnswer.setOrderNumber(editedComplexAnswer.getOrderNumber());
            editedComplexAnswer.setExerciseValues(
                    complex.getEditedExercises().stream().map(this::map).collect(Collectors.toList())
            );
            return editedComplexAnswer;
        }).collect(Collectors.toList()));
        dto.setExercises(appointedTraining.getEditedExercises().stream().map(this::map).collect(Collectors.toList()));
        return dto;
    }

    @Override
    public EditedExerciseAnswer map(EditedExercise editedExercise) {
        EditedExerciseAnswer dto = new EditedExerciseAnswer();
        dto.setExerciseValues(editedExercise.getExerciseValues());
        dto.setOrderNumber(editedExercise.getOrderNumber());
        dto.setExerciseId(editedExercise.getId());
        return dto;
    }

    private ShortAppointedTrainingDto map(AppointedTraining appointedTraining) {
        ShortAppointedTrainingDto dto = new ShortAppointedTrainingDto();
        dto.setDates(appointedTraining.getDates());
        dto.setName(appointedTraining.getName());
        dto.setTrainerName(appointedTraining.getTrainer().getUser().getName());
        dto.setId(appointedTraining.getId());
        return dto;
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
        if(editedComplex.getEditedExercises() != null) {
            editedComplex.setEditedExercises(complex.getExercises().stream().map(this::map).collect(Collectors.toList()));
        }
        editedComplex = editedComplexRepository.save(editedComplex);
        return editedComplex;
    }

    private void checkOrder(List<Integer> complexOrder, List<Integer> exercisesOrder) {
        int counter = 1;

        if(complexOrder == null) {
            for (Integer integer : exercisesOrder) {
                checkCounter(counter, integer);
                counter++;
            }
            return;
        }
        if(exercisesOrder == null) {
            for (Integer integer : complexOrder) {
                checkCounter(counter, integer);
                counter++;
            }
            return;
        }

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
