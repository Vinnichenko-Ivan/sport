package com.hits.sport.dto.trainer;

import lombok.Data;

import java.util.UUID;

@Data
public class AddToTrainerUserDto {
    private UUID queryId;
    private String login;
    private String name;
}
