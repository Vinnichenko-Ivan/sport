package com.hits.sport.service.impl;

import com.hits.sport.exception.BadRequestException;
import com.hits.sport.model.Token;
import com.hits.sport.model.TokenType;
import com.hits.sport.model.User;
import com.hits.sport.repository.TokenRepository;
import com.hits.sport.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public String generateToken(User user, TokenType type) {
        String tokenValue = "";
        Date expDate = null;
        if(type == TokenType.CONFIRM) {
            tokenValue = generateToken(true, 20);
            expDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
        }
        else if(type == TokenType.PASSWORD_CHANGE) {
            tokenValue = generateToken(false, 8);
            expDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
        }
        else if(type == TokenType.RESTORE) {
            tokenValue = generateToken(true, 15);
            expDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
        }
        Token token = new Token();
        token.setToken(tokenValue);
        token.setUser(user);
        token.setType(type);
        token.setExpDate(expDate);
        tokenRepository.save(token);
        return tokenValue;
    }

    @Override
    public User getByToken(String token, TokenType type, Boolean delete) {
        Token tokenEntity = tokenRepository.findByTokenAndAndType(token, type).orElseThrow(()->new BadRequestException("bad token"));
        if(tokenEntity.getExpDate().before(new Date(System.currentTimeMillis()))) {
            throw new BadRequestException("bad token");
        }
        User user = tokenEntity.getUser();
        if(delete) {
            tokenRepository.delete(tokenEntity);
        }
        return user;
    }

    @Override
    public void deleteToken(User user, TokenType type) {
        tokenRepository.deleteAll(tokenRepository.findAllByUserAndType(user, type));
    }

    private String generateToken(Boolean alphabet, int size) {
        String alphabetsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetsInLowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        // create a super set of all characters
        String allCharacters = (alphabet?(alphabetsInLowerCase + alphabetsInUpperCase):"") + numbers;
        // initialize a string to hold result
        StringBuilder randomString = new StringBuilder();
        // loop for 10 times
        for (int i = 0; i < size; i++) {
            // generate a random number between 0 and length of all characters
            int randomIndex = (int)(Math.random() * allCharacters.length());
            // retrieve character at index and add it to result
            randomString.append(allCharacters.charAt(randomIndex));
        }
        return randomString.toString();
    }
}
