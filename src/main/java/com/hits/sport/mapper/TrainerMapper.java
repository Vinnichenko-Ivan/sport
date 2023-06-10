package com.hits.sport.mapper;

import com.hits.sport.dto.trainer.ShortTrainerDto;
import com.hits.sport.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrainerMapper {
    @Mapping(target = "name", source = "user.name")
    ShortTrainerDto map(Trainer trainer);
}
