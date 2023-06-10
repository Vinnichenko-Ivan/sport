package com.hits.sport.dto.trainer;

import lombok.Data;

import java.util.UUID;

@Data
public class ShortTrainerDto {
    private UUID id;
    private String name;
    private String shortName;
}
