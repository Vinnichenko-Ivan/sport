package com.hits.sport.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class GroupEditDto {
    private String name;
    private UUID mainTrainer;
    private UUID imageId;
}
