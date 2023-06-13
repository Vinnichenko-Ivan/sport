package com.hits.sport.dto.training;

import lombok.Data;

import java.util.UUID;

@Data
public class ShortTrainingDto {
    private UUID id;
    private String name;
    private String description;
    private Boolean common;
}
