package com.hits.sport.service.impl;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.trainer.AddToTrainerUserDto;
import com.hits.sport.dto.trainer.ShortTrainerDto;
import com.hits.sport.dto.user.ShortUserDto;
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
import java.util.HashSet;
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
        Page<AddToTrainerQuery> page = addToTrainerQueryRepository.findAll(new AddToTrainerQuerySpecification(name, trainer), Utils.toPageable(paginationQueryDto, orders));

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
    public void acceptQuery(UUID queryId) {
        setQueryStatus(queryId, true);
    }

    @Override

    public void rejectQuery(UUID queryId) {
        setQueryStatus(queryId, false);
    }

    @Transactional
    public void setQueryStatus(UUID queryId, Boolean isAccept) {
        UUID id = jwtProvider.getId();//62851366-2b94-4262-8511-6ea3cbf524d2,2023-06-13 09:56:14.092000,75bfd167-4cfa-4fc7-a9ff-832ddeaf79c8,aa31d535-5754-4568-bbe3-d44faf1fccee
        AddToTrainerQuery query = addToTrainerQueryRepository.findById(queryId).orElseThrow(()->new NotFoundException("query not found"));
        Trainer trainer = query.getTrainer();
        if(!trainer.getId().equals(id)) {
            throw new NotFoundException("query not found");
        }
        if(isAccept) {
            query.getUser().getTrainers().add(query.getTrainer());
            userRepository.save(query.getUser());
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
