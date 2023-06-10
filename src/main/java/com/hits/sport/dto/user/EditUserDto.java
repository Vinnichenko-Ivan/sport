package com.hits.sport.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.hits.sport.config.RegexConfig.EMAIL;

@Data
public class EditUserDto {
    @NotBlank
    @ApiModelProperty(notes = "name", example = "Pavel", required = true)
    private String name;
    @NotNull
    @Pattern(regexp = EMAIL)
    @ApiModelProperty(notes = "email", example = "jora@gmail.com", required = true)
    private String email;
}
