package com.hits.sport.mapper;

import com.hits.sport.dto.group.GroupDto;
import com.hits.sport.dto.group.GroupEditDto;
import com.hits.sport.dto.group.ShortGroupDto;
import com.hits.sport.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDto map(Group group);

    @Mapping(target = "mainTrainerName", source = "mainTrainer.user.name")
    ShortGroupDto mapToShort(Group group);

    @Mapping(target = "mainTrainer", ignore = true)
    void map(@MappingTarget Group group, GroupEditDto groupEditDto);
}
