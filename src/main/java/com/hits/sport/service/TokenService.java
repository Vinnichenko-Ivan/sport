package com.hits.sport.service;

import com.hits.sport.model.token.TokenType;
import com.hits.sport.model.User;

public interface TokenService {
    String generateToken(User user, TokenType type);
    User getByToken(String token, TokenType type, Boolean delete);

    void deleteToken(User user, TokenType type);
    String generateToken(Boolean alphabet, int size);
}
