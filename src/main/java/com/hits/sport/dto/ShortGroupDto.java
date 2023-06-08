package com.hits.sport.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ShortGroupDto {
    private UUID id;
    private String name;
    private int personCount;
    private String mainTrainerName;
}
