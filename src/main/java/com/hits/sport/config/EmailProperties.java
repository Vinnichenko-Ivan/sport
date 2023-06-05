package com.hits.sport.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("email")
public class EmailProperties {
    private String email;
    private String password;
}
