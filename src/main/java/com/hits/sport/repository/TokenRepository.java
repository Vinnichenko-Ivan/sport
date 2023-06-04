package com.hits.sport.repository;

import com.hits.sport.model.Token;
import com.hits.sport.model.TokenType;
import com.hits.sport.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends CrudRepository<Token, UUID> {
    Optional<Token> findByToken(String token);
    Optional<Token> findByTokenAndAndType(String token, TokenType type);
    List<Token> findAllByUserAndType(User user, TokenType type);
}
