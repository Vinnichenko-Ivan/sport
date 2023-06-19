package com.hits.sport.repository;

import com.hits.sport.model.token.Token;
import com.hits.sport.model.token.TokenType;
import com.hits.sport.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends CrudRepository<Token, UUID> {
    Optional<Token> findByToken(String token);
    Optional<Token> findByTokenAndAndType(String token, TokenType type);
    List<Token> findAllByUserAndType(User user, TokenType type);
}
