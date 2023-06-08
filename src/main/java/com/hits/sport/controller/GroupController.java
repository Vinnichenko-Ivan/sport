package com.hits.sport.controller;

import com.hits.sport.dto.*;
import com.hits.sport.service.GroupService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;

import static com.hits.sport.utils.Path.*;

@Api
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class GroupController {
    private final GroupService groupService;

    @PostMapping(GROUP_URL)
    public GroupDto createGroup(String name) {
        return groupService.createGroup(name);
    }

    @PostMapping(GROUPS_MY_URL)
    public PaginationAnswerDto<ShortGroupDto> myGroups(String name, @Valid @RequestBody PaginationQueryDto paginationQueryDto) {
        return groupService.myGroups(name, paginationQueryDto);
    }

    @PostMapping(GROUPS_TRAINING_URL)
    public PaginationAnswerDto<ShortGroupDto> myTrainingGroups(String name, @Valid @RequestBody PaginationQueryDto paginationQueryDto) {
        return groupService.myTrainingGroups(name, paginationQueryDto);
    }

    @PutMapping(GROUP_EDIT_URL)
    public GroupDto editGroup(@PathVariable UUID groupId, @Valid @RequestBody GroupEditDto groupEditDto) {
        return groupService.editGroup(groupId, groupEditDto);
    }

    @DeleteMapping(GROUP_EDIT_URL)
    public void deleteGroup(@PathVariable UUID groupId) {
        groupService.deleteGroup(groupId);
    }

    @PostMapping(GROUP_USERS)
    public void addUsers(@PathVariable UUID groupId, @Valid @RequestBody Set<UUID> ids) {
        groupService.addUsers(groupId, ids);
    }

    @DeleteMapping(GROUP_USERS)
    public void deleteUsers(@PathVariable UUID groupId, @Valid @RequestBody Set<UUID> ids) {
        groupService.deleteUsers(groupId, ids);
    }

    @GetMapping(GROUP_USERS)
    public void getUsers(@PathVariable UUID groupId) {
        groupService.getUsers(groupId);
    }

    @PostMapping(GROUP_TRAINERS)
    public void addTrainers(@PathVariable UUID groupId, @Valid @RequestBody Set<UUID> ids) {
        groupService.addTrainers(groupId, ids);
    }

    @DeleteMapping(GROUP_TRAINERS)
    public void deleteTrainers(@PathVariable UUID groupId, @Valid @RequestBody Set<UUID> ids) {
        groupService.deleteTrainers(groupId, ids);
    }

    @GetMapping(GROUP_TRAINERS)
    public void getTrainers(@PathVariable UUID groupId) {
        groupService.getTrainers(groupId);
    }
}
