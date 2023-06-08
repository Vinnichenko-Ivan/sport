package com.hits.sport.service;

import com.hits.sport.dto.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface GroupService {
    GroupDto createGroup(String name);

    PaginationAnswerDto<ShortGroupDto> myGroups(String name, PaginationQueryDto paginationQueryDto);

    PaginationAnswerDto<ShortGroupDto> myTrainingGroups(String name, PaginationQueryDto paginationQueryDto);

    GroupDto editGroup(UUID groupId, GroupEditDto groupEditDto);

    void deleteGroup(UUID groupId);

    void addUsers(UUID groupId, Set<UUID> ids);

    void deleteUsers(UUID groupId, Set<UUID> ids);
    List<ShortUserDto> getUsers(UUID groupId);

    void addTrainers(UUID groupId, Set<UUID> ids);

    void deleteTrainers(UUID groupId, Set<UUID> ids);

    List<ShortTrainerDto> getTrainers(UUID groupId);
}
