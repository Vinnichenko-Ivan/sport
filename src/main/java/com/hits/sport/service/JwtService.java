package com.hits.sport.service;

import com.hits.sport.model.User;
import org.springframework.lang.NonNull;

public interface JwtService {
    String generateAccessToken(@NonNull User user);

}
