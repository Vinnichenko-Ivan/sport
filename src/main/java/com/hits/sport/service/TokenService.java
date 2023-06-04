package com.hits.sport.service;

import com.hits.sport.model.TokenType;
import com.hits.sport.model.User;
import net.bytebuddy.description.method.MethodDescription;

public interface TokenService {
    String generateToken(User user, TokenType type);
    User getByToken(String token, TokenType type, Boolean delete);

    void deleteToken(User user, TokenType type);
}
