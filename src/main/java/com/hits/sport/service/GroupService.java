package com.hits.sport.service;

import com.hits.sport.dto.group.GroupCreateDto;
import com.hits.sport.dto.group.GroupDto;
import com.hits.sport.dto.group.GroupEditDto;
import com.hits.sport.dto.group.ShortGroupDto;
import com.hits.sport.dto.trainer.ShortTrainerDto;
import com.hits.sport.dto.user.ShortUserDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface GroupService {
    GroupDto createGroup(String name, UUID imageId);

    List<ShortGroupDto> myGroups(String name);

    List<ShortGroupDto> myTrainingGroups(String name);

    GroupDto editGroup(UUID groupId, GroupEditDto groupEditDto);

    void deleteGroup(UUID groupId);

    void addUsers(UUID groupId, Set<UUID> ids);

    void deleteUsers(UUID groupId, Set<UUID> ids);
    List<ShortUserDto> getUsers(UUID groupId);

    void addTrainers(UUID groupId, Set<UUID> ids);

    void deleteTrainers(UUID groupId, Set<UUID> ids);

    List<ShortTrainerDto> getTrainers(UUID groupId);

    void createGroup(GroupCreateDto groupCreateDto);
}
