package com.hits.sport.service.impl;

import com.hits.sport.dto.group.*;
import com.hits.sport.dto.trainer.ShortTrainerDto;
import com.hits.sport.dto.user.ShortUserDto;
import com.hits.sport.exception.BadRequestException;
import com.hits.sport.exception.ForbiddenException;
import com.hits.sport.exception.NotFoundException;
import com.hits.sport.mapper.GroupMapper;
import com.hits.sport.mapper.TrainerMapper;
import com.hits.sport.mapper.UserMapper;
import com.hits.sport.model.Group;
import com.hits.sport.model.Trainer;
import com.hits.sport.model.User;
import com.hits.sport.repository.GroupRepository;
import com.hits.sport.repository.TrainerRepository;
import com.hits.sport.repository.UserRepository;
import com.hits.sport.service.GroupService;
import com.hits.sport.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final JwtProvider jwtProvider;
    private final GroupMapper groupMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TrainerMapper trainerMapper;
    private final TrainerRepository trainerRepository;

    @Override
    public GroupDto createGroup(String name, UUID imageId) {
        User user = jwtProvider.getUser();
        Trainer trainer = user.getTrainer();
        if(trainer == null) {
            throw new ForbiddenException("not trainer");
        }
        Set<Trainer> trainers = new HashSet<>();
        trainers.add(trainer);
        Group group = new Group();
        group.setUsers(new HashSet<>());
        group.setName(name);
        group.setMainTrainer(trainer);
        group.setTrainers(trainers);
        group.setImageId(imageId);
        group = groupRepository.save(group);
        return map(groupMapper.map(group), group);
    }

    @Override
    public List<ShortGroupDto> myGroups(String name) {
        if(name == null) {
            name = "";
        }
        User user = jwtProvider.getUser();
        String finalName = name;
        List<Group> groups = user.getGroups().stream().filter(
                (group) -> {
                    return group.getName().toLowerCase().matches("(.*)" + finalName.toLowerCase() + "(.*)");
                }
        ).collect(Collectors.toList());
        return groups.stream().map(groupMapper::mapToShort).collect(Collectors.toList());
    }

    @Override
    public List<ShortGroupDto> myTrainingGroups(String name) {
        if(name == null) {
            name = "";
        }
        User user = jwtProvider.getUser();
        if(user.getTrainer() == null) {
            throw new BadRequestException("not trainer");
        }
        String finalName = name;
        return user.getTrainer().getGroups().stream().filter(
                (group) -> {
                    Boolean m = group.getName().toLowerCase().matches("(.*)" + finalName.toLowerCase() + "(.*)");
                    return m && group.getTrainers().contains(jwtProvider.getUser().getTrainer());
                })
                .map(groupMapper::mapToShort).collect(Collectors.toList());
    }

    @Override
    public GroupDto editGroup(UUID groupId, GroupEditDto groupEditDto) {
        Group group = groupRepository.findById(groupId).orElseThrow(()->new NotFoundException("group not found"));
        User user = jwtProvider.getUser();
        checkMainTrainerRules(group, user);
        group.setName(groupEditDto.getName());
        group.setImageId(groupEditDto.getImageId());
        group.setDescription(groupEditDto.getDescription());
        if(groupEditDto.getMainTrainer() != null) {
            User trainer = userRepository.findById(groupEditDto.getMainTrainer()).orElseThrow(()->new NotFoundException("trainer not found"));
            if(trainer.getTrainer() == null) {
                throw new NotFoundException("trainer not found");
            }
            group.setMainTrainer(trainer.getTrainer());
        }
        group = groupRepository.save(group);
        return map(groupMapper.map(group), group);
    }

    @Override
    public GroupDto editFullGroup(UUID groupId, GroupFullEditDto groupEditDto) {
        Group group = groupRepository.findById(groupId).orElseThrow(()->new NotFoundException("group not found"));
        User user = jwtProvider.getUser();
        checkMainTrainerRules(group, user);
        group.setName(groupEditDto.getName());
        group.setImageId(groupEditDto.getImageId());
        group.setDescription(groupEditDto.getDescription());
        if(groupEditDto.getMainTrainer() != null) {
            User trainer = userRepository.findById(groupEditDto.getMainTrainer()).orElseThrow(()->new NotFoundException("trainer not found"));
            if(trainer.getTrainer() == null) {
                throw new NotFoundException("trainer not found");
            }
            group.setMainTrainer(trainer.getTrainer());
        }
        group = groupRepository.save(group);

        Set<Trainer> trainers = new HashSet<>();
        for(UUID id:groupEditDto.getTrainers()) {
            Trainer trainerToAdd = trainerRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("User %s not found", id.toString())));
            trainers.add(trainerToAdd);
        }
        group.setTrainers(trainers);

        Set<User> users = new HashSet<>();
        for(UUID id:groupEditDto.getTrainers()) {
            User userToAdd = userRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("User %s not found", id.toString())));
            users.add(userToAdd);
        }
        group.setUsers(users);
        return map(groupMapper.map(group), group);
    }

    @Override
    public void deleteGroup(UUID groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(()->new NotFoundException("group not found"));
        User user = jwtProvider.getUser();
        checkMainTrainerRules(group, user);
        groupRepository.delete(group);
    }

    @Override
    @Transactional
    public void addUsers(UUID groupId, Set<UUID> ids) {
        Group group = groupRepository.findById(groupId).orElseThrow(()->new NotFoundException("group not found"));
        User user = jwtProvider.getUser();
        checkTrainerRules(group, user);
        if(ids == null) {
            return;
        }
        for(UUID id:ids) {
            User userToAdd = userRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("User %s not found", id.toString())));
            if(group.getUsers().contains(userToAdd)) {
                throw new BadRequestException(String.format("user %s already in group", id));
            }
            group.getUsers().add(userToAdd);
        }
        groupRepository.save(group);
    }

    @Override
    public void deleteUsers(UUID groupId, Set<UUID> ids) {
        Group group = groupRepository.findById(groupId).orElseThrow(()->new NotFoundException("group not found"));
        User user = jwtProvider.getUser();
        checkTrainerRules(group, user);
        if(ids == null) {
            return;
        }
        for(UUID id:ids) {
            User userToDelete = userRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("User %s not found", id.toString())));
            if(!group.getUsers().contains(userToDelete)) {
                throw new BadRequestException(String.format("user %s already in group", id));
            }
            group.getUsers().remove(userToDelete);
        }
        groupRepository.save(group);
    }

    @Override
    public List<ShortUserDto> getUsers(UUID groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(()->new NotFoundException("group not found"));
        User user = jwtProvider.getUser();
        checkTrainerRules(group, user);
        return group.getUsers().stream().map(userMapper::mapToShort).collect(Collectors.toList());
    }

    @Override
    public void addTrainers(UUID groupId, Set<UUID> ids) {
        Group group = groupRepository.findById(groupId).orElseThrow(()->new NotFoundException("group not found"));
        User user = jwtProvider.getUser();
        checkMainTrainerRules(group, user);
        if(ids == null) {
            return;
        }
        for(UUID id:ids) {
            User userToAdd = userRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("User %s not found", id.toString())));
            if(userToAdd.getTrainer() == null) {
                throw new BadRequestException(String.format("user %s not trainer", id));
            }
            if(group.getTrainers().contains(userToAdd.getTrainer())) {
                throw new BadRequestException(String.format("user %s already in group", id));
            }
            group.getTrainers().add(userToAdd.getTrainer());
        }
        groupRepository.save(group);
    }

    @Override
    public void deleteTrainers(UUID groupId, Set<UUID> ids) {
        Group group = groupRepository.findById(groupId).orElseThrow(()->new NotFoundException("group not found"));
        User user = jwtProvider.getUser();
        checkMainTrainerRules(group, user);
        if(ids == null) {
            return;
        }
        for(UUID id:ids) {
            User userToDelete = userRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("User %s not found", id.toString())));
            if(userToDelete.getTrainer() == null) {
                throw new BadRequestException(String.format("user %s not trainer", id));
            }
            if(!group.getTrainers().contains(userToDelete.getTrainer())) {
                throw new BadRequestException(String.format("user %s already in group", id));
            }
            group.getTrainers().remove(userToDelete.getTrainer());
        }
        groupRepository.save(group);
    }

    @Override
    public List<ShortTrainerDto> getTrainers(UUID groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(()->new NotFoundException("group not found"));
        User user = jwtProvider.getUser();
        try{
            checkTrainerRules(group, user);
        } catch (ForbiddenException e) {
            checkMemberRules(group, user);
        }

        return group.getTrainers().stream().map(trainerMapper::map).collect(Collectors.toList());
    }

    void checkMainTrainerRules(Group group, User user) {
        Trainer trainer = user.getTrainer();
        if(trainer == null) {
            throw new ForbiddenException("not trainer");
        }
        else if(group.getMainTrainer().getId().equals(trainer.getId())) {
            return;
        }
        else {
            throw new ForbiddenException("not allowed");
        }
    }

    void checkTrainerRules(Group group, User user) {
        Trainer trainer = user.getTrainer();
        if(trainer == null) {
            throw new ForbiddenException("not trainer");
        }
        else if(group.getTrainers().contains(trainer)) {
            return;
        }
        else if(group.getMainTrainer().getId().equals(trainer.getId())) {
            return;
        }
        else {
            throw new ForbiddenException("not allowed");
        }
    }

    void checkMemberRules(Group group, User user) {
        if(!group.getUsers().contains(user)) {
            throw new ForbiddenException("not allowed");
        }
    }

    @Override
    @Transactional
    public void createGroup(GroupCreateDto groupCreateDto) {
        User user = jwtProvider.getUser();
        Trainer trainer = user.getTrainer();
        if(trainer == null) {
            throw new ForbiddenException("not trainer");
        }
        Set<Trainer> trainers = new HashSet<>();
        trainers.add(trainer);
        Group group = new Group();
        group.setUsers(new HashSet<>());
        group.setName(groupCreateDto.getName());
        group.setMainTrainer(trainer);
        group.setTrainers(trainers);
        group.setDescription(groupCreateDto.getDescription());
        group = groupRepository.save(group);

        addTrainers(group.getId(), groupCreateDto.getTrainers());
        addUsers(group.getId(), groupCreateDto.getUsers());
    }

    @Override
    public GroupDto getGroup(UUID groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(()->new NotFoundException("group not found"));
        return map(groupMapper.map(group), group);
    }

    private GroupDto map(GroupDto dto, Group group) {
        dto.setMainTrainer(trainerMapper.map(group.getMainTrainer()));
        dto.setTrainerDtos(group.getTrainers().stream().map(trainerMapper::map).collect(Collectors.toSet()));
        dto.setUsers(group.getUsers().stream().map(userMapper::mapToShort).collect(Collectors.toSet()));
        return dto;
    }
}
