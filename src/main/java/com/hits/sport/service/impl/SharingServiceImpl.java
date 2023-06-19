package com.hits.sport.service.impl;

import com.hits.sport.dto.sharing.SharingTokenCreateDto;
import com.hits.sport.dto.sharing.SharingTokensDto;
import com.hits.sport.exception.ForbiddenException;
import com.hits.sport.exception.NotFoundException;
import com.hits.sport.mapper.ComplexMapper;
import com.hits.sport.mapper.ExerciseMapper;
import com.hits.sport.mapper.TrainerMapper;
import com.hits.sport.mapper.TrainingMapper;
import com.hits.sport.model.Trainer;
import com.hits.sport.model.template.ComplexTemplate;
import com.hits.sport.model.token.SharingToken;
import com.hits.sport.model.token.Token;
import com.hits.sport.repository.ComplexRepository;
import com.hits.sport.repository.ExerciseTemplateRepository;
import com.hits.sport.repository.SharedTokenRepository;
import com.hits.sport.repository.TrainingRepository;
import com.hits.sport.service.SharingService;
import com.hits.sport.service.TokenService;
import com.hits.sport.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SharingServiceImpl implements SharingService {
    private final SharedTokenRepository sharedTokenRepository;
    private final TokenService tokenService;
    private final JwtProvider jwtProvider;
    private final ExerciseTemplateRepository exerciseTemplateRepository;
    private final ComplexRepository complexRepository;
    private final TrainingRepository trainingRepository;
    private final ExerciseMapper exerciseMapper;
    private final TrainingMapper trainingMapper;
    private final ComplexMapper complexMapper;
    @Override
    public String createToken(SharingTokenCreateDto sharingTokenCreateDto) {
        SharingToken sharingToken = new SharingToken();
        sharingToken.setTrainer(getTrainer());
        sharingToken.setExpDate(sharingTokenCreateDto.getExpDate());
        sharingToken.setMaxCount(sharingTokenCreateDto.getMaxCount());
        sharingToken.setValue(tokenService.generateToken(false, 8));
        sharingToken.setTrainings(
                sharingTokenCreateDto.getTrainings().stream().map(
                        (id)->{
                            return trainingRepository.findById(id).orElseThrow(NotFoundException::new);
                        }
                ).collect(Collectors.toList())
        );
        sharingToken.setComplexes(
                sharingTokenCreateDto.getComplexes().stream().map(
                        (id)->{
                            return complexRepository.findById(id).orElseThrow(NotFoundException::new);
                        }
                ).collect(Collectors.toList())
        );
        sharingToken.setExercises(
                sharingTokenCreateDto.getExercises().stream().map(
                        (id)->{
                            return exerciseTemplateRepository.findById(id).orElseThrow(NotFoundException::new);
                        }
                ).collect(Collectors.toList())
        );
        sharingToken = sharedTokenRepository.save(sharingToken);
        return sharingToken.getValue();
    }

    @Override
    public void useToken(String value) {
        Trainer trainer = getTrainer();
        SharingToken sharingToken = sharedTokenRepository.getByValue(value).orElseThrow(NotFoundException::new);
        if(sharingToken.getExpDate().before(new Date(System.currentTimeMillis()))) {
            sharedTokenRepository.delete(sharingToken);
            throw new NotFoundException();
        }
        if(sharingToken.getMaxCount() <= 0) {
            sharedTokenRepository.delete(sharingToken);
            throw new NotFoundException();
        }
        for(var entity : sharingToken.getComplexes()) {
            entity.getAllowed().add(trainer);
            complexRepository.save(entity);
        }
        for(var entity : sharingToken.getTrainings()) {
            entity.getAllowed().add(trainer);
            trainingRepository.save(entity);
        }
        for(var entity : sharingToken.getExercises()) {
            entity.getAllowed().add(trainer);
            exerciseTemplateRepository.save(entity);
        }
        if(sharingToken.getMaxCount() == 1) {
            sharedTokenRepository.delete(sharingToken);
        }
        if(sharingToken.getMaxCount() != null) {
            sharingToken.setMaxCount(sharingToken.getMaxCount() - 1);
        }
    }

    @Override
    public List<SharingTokensDto> getMy() {
        Trainer trainer = getTrainer();
        List<SharingToken> list = sharedTokenRepository.getByTrainer(trainer);
        return list.stream().map(
                (sharingToken) -> {
                    SharingTokensDto sharingTokensDto = new SharingTokensDto();
                    sharingTokensDto.setId(sharingTokensDto.getId());
                    sharingTokensDto.setMaxCount(sharingToken.getMaxCount());
                    sharingTokensDto.setExpDate(sharingToken.getExpDate());
                    sharingTokensDto.setComplexes(sharingToken.getComplexes().stream().map(complexMapper::mapToShort).collect(Collectors.toList()));
                    sharingTokensDto.setExercises(sharingToken.getExercises().stream().map(exerciseMapper::mapToShort).collect(Collectors.toList()));
                    sharingTokensDto.setTrainings(sharingToken.getTrainings().stream().map(trainingMapper::mapToShort).collect(Collectors.toList()));
                    return sharingTokensDto;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public void deleteToken(UUID id) {
        Trainer trainer = getTrainer();
        SharingToken sharingToken = sharedTokenRepository.findById(id).orElseThrow(NotFoundException::new);
        if(!sharingToken.getTrainer().equals(trainer)) {
            throw new ForbiddenException();
        }
        sharedTokenRepository.delete(sharingToken);
    }

    private Trainer getTrainer() {
        Trainer trainer = jwtProvider.getUser().getTrainer();
        if(trainer == null) {
            throw new ForbiddenException();
        }
        return trainer;
    }
}
