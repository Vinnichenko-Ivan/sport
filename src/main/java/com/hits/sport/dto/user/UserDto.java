package com.hits.sport.dto.user;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String login;
    private String name;
    private Boolean isTrainer;
}
