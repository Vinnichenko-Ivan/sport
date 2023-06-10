package com.hits.sport.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
    private String restoreToken;
    private String accessToken;
}
