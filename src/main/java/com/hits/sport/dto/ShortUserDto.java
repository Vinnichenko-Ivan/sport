package com.hits.sport.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ShortUserDto {
    private UUID id;
    private String name;
    private String login;
    private UUID imageId;
}
