package com.hits.sport.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.hits.sport.config.RegexConfig.*;

@Data
public class RegisterDto {
    @NotNull
    @Pattern(regexp = EMAIL)
    @ApiModelProperty(notes = "email", example = "jora@gmail.com", required = true)
    private String email;

    @NotNull
    @Pattern(regexp = LOGIN)
    @ApiModelProperty(notes = "login", example = "term123", required = true)
    private String login;

    @NotNull
    @Pattern(regexp = PASSWORD)
    @ApiModelProperty(notes = "password", example = "Strong@12", required = true)
    private String password;

    @ApiModelProperty(notes = "name", example = "Pavel", required = false)
    private String name;
}
