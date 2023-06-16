package com.hits.sport.dto.group;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class GroupFullEditDto {
    private String name;
    private UUID mainTrainer;
    private UUID imageId;
    private String description;
    private Set<UUID> users;
    private Set<UUID> trainers;
}
