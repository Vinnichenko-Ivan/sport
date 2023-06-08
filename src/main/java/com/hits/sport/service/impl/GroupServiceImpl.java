package com.hits.sport.service.impl;

import com.hits.sport.dto.*;
import com.hits.sport.exception.NotImplementedException;
import com.hits.sport.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    @Override
    public GroupDto createGroup(String name) {
        throw new NotImplementedException();
    }

    @Override
    public PaginationAnswerDto<ShortGroupDto> myGroups(String name, PaginationQueryDto paginationQueryDto) {
        throw new NotImplementedException();
    }

    @Override
    public PaginationAnswerDto<ShortGroupDto> myTrainingGroups(String name, PaginationQueryDto paginationQueryDto) {
        throw new NotImplementedException();
    }

    @Override
    public GroupDto editGroup(UUID groupId, GroupEditDto groupEditDto) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteGroup(UUID groupId) {
        throw new NotImplementedException();
    }

    @Override
    public void addUsers(UUID groupId, Set<UUID> ids) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteUsers(UUID groupId, Set<UUID> ids) {
        throw new NotImplementedException();
    }

    @Override
    public List<ShortUserDto> getUsers(UUID groupId) {
        throw new NotImplementedException();
    }

    @Override
    public void addTrainers(UUID groupId, Set<UUID> ids) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteTrainers(UUID groupId, Set<UUID> ids) {
        throw new NotImplementedException();
    }

    @Override
    public List<ShortTrainerDto> getTrainers(UUID groupId) {
        throw new NotImplementedException();
    }
}
