package com.hits.sport.service.impl;

import com.hits.sport.dto.*;
import com.hits.sport.dto.ShortTrainerDto;
import com.hits.sport.exception.BadRequestException;
import com.hits.sport.exception.ForbiddenException;
import com.hits.sport.exception.NotFoundException;
import com.hits.sport.mapper.TrainerMapper;
import com.hits.sport.model.*;
import com.hits.sport.repository.AddToTrainerQueryRepository;
import com.hits.sport.repository.TrainerRepository;
import com.hits.sport.repository.UserRepository;
import com.hits.sport.repository.specification.AddToTrainerQuerySpecification;
import com.hits.sport.repository.specification.TrainerShortNameSpecification;
import com.hits.sport.service.TrainerService;
import com.hits.sport.utils.JwtProvider;
import com.hits.sport.utils.Utils;
import com.hits.sport.repository.specification.MyUserNameSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private final JwtProvider jwtProvider;
    private final AddToTrainerQueryRepository addToTrainerQueryRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;
    @Override
    public PaginationAnswerDto<ShortTrainerDto> getTrainer(String shortName, PaginationQueryDto paginationQueryDto) {
        List<Sort.Order> orders = List.of(
                new Sort.Order(Sort.Direction.ASC, Trainer_.shortName.getName())
        );
        Page<Trainer> page = trainerRepository.findAll(new TrainerShortNameSpecification(shortName), Utils.toPageable(paginationQueryDto, orders));
        PaginationAnswerDto<ShortTrainerDto> dto = Utils.toAnswerWData(page);
        dto.setData(page.getContent().stream().map(trainerMapper::map).collect(Collectors.toList()));
        return dto;
    }

    @Override
    public void addToTrainer(UUID trainerId) {
        User user = jwtProvider.getUser();
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(() -> new NotFoundException("trainer not found"));
        if(trainer.getUsers().contains(user)) {
            throw new BadRequestException("you already in trainer group");
        }
        if(addToTrainerQueryRepository.existsByUserAndTrainer(user, trainer)) {
            throw new BadRequestException("you already send query");
        }
        AddToTrainerQuery addToTrainerQuery = new AddToTrainerQuery();
        addToTrainerQuery.setTrainer(trainer);
        addToTrainerQuery.setUser(user);
        addToTrainerQueryRepository.save(addToTrainerQuery);
    }

    @Override
    public PaginationAnswerDto<AddToTrainerUserDto> getMyQuery(String name, PaginationQueryDto paginationQueryDto) {
        User user = jwtProvider.getUser();
        Trainer trainer = user.getTrainer();
        if(trainer == null) {
            throw new ForbiddenException("not trainer");
        }
        List<Sort.Order> orders = List.of(
                new Sort.Order(Sort.Direction.ASC, AddToTrainerQuery_.createdDate.getName())
        );
        Page<AddToTrainerQuery> page = addToTrainerQueryRepository.findAll(new AddToTrainerQuerySpecification(name), Utils.toPageable(paginationQueryDto, orders));

        PaginationAnswerDto<AddToTrainerUserDto> dto = Utils.toAnswerWData(page);
        dto.setData(page.getContent().stream().map((query) -> {
            AddToTrainerUserDto dtoTemp = new AddToTrainerUserDto();
            dtoTemp.setQueryId(query.getId());
            dtoTemp.setLogin(query.getUser().getLogin());
            dtoTemp.setName(query.getUser().getName());
            return dtoTemp;
        }).collect(Collectors.toList()));
        return dto;
    }

    @Override
    @Transactional
    public void acceptQuery(UUID queryId) {
        setQueryStatus(queryId, true);
    }

    @Override
    @Transactional
    public void rejectQuery(UUID queryId) {
        setQueryStatus(queryId, false);
    }

    private void setQueryStatus(UUID queryId, Boolean isAccept) {
        UUID id = jwtProvider.getId();
        AddToTrainerQuery query = addToTrainerQueryRepository.findById(queryId).orElseThrow(()->new NotFoundException("query not found"));
        Trainer trainer = query.getTrainer();
        if(!trainer.getId().equals(id)) {
            throw new NotFoundException("query not found");
        }
        if(isAccept) {
            trainer.getUsers().add(query.getUser());
            trainerRepository.save(trainer);
        }
        addToTrainerQueryRepository.delete(query);
    }

    @Override
    public PaginationAnswerDto<ShortUserDto> getMyUsers(String name, PaginationQueryDto paginationQueryDto) {
        User user = jwtProvider.getUser();
        Trainer trainer = user.getTrainer();
        if(trainer == null) {
            throw new ForbiddenException("not trainer");
        }

        Page<User> page = userRepository.findAll(new MyUserNameSpecification(trainer, name), Utils.toPageable(paginationQueryDto));
        PaginationAnswerDto<ShortUserDto> dto = Utils.toAnswerWData(page);
        dto.setData(
                page.getContent().stream().map( (userTemp) -> {
                        ShortUserDto shortUserDto = new ShortUserDto();
                        shortUserDto.setId(userTemp.getId());
                        shortUserDto.setName(userTemp.getName());
                        shortUserDto.setLogin(userTemp.getLogin());
                        return shortUserDto;
                        }
                ).collect(Collectors.toList())
        );
        return dto;
    }
}
