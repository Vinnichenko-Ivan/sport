package com.hits.sport.dto.group;

import com.hits.sport.dto.trainer.ShortTrainerDto;
import com.hits.sport.dto.user.ShortUserDto;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class GroupCreateDto {
    private UUID id;
    private String name;
    private Set<UUID> users;
    private Set<UUID> trainers;
}
