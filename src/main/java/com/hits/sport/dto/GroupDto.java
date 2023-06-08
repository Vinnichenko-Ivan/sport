package com.hits.sport.dto;

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
}
