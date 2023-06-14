package com.hits.sport.dto.group;

import com.hits.sport.dto.user.ShortUserDto;
import com.hits.sport.dto.trainer.ShortTrainerDto;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class GroupDto {
    private UUID id;
    private String name;
    private Set<ShortUserDto> users;
    private Set<ShortTrainerDto> trainerDtos;
    private ShortTrainerDto mainTrainer;
    private String description;
}
