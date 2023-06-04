package com.hits.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
    private String restoreToken;
    private String accessToken;
}
