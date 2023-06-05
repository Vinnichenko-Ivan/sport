package com.hits.sport.service.impl;

import com.hits.sport.exception.AuthException;
import com.hits.sport.model.User;
import com.hits.sport.repository.UserRepository;
import com.hits.sport.service.JwtService;
import com.hits.sport.utils.JwtProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Log4j2
public class JwtServiceImpl implements JwtService {
    private final SecretKey jwtAccessSecret;

    private final JwtProvider jwtProvider;

    private final UserRepository userRepository;
    public JwtServiceImpl(@Value("${jwt.secret.access}") String secret, JwtProvider jwtProvider, UserRepository userRepository) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    @Override
    public String generateAccessToken(User user) {
        final Date accessExpiration = new Date(System.currentTimeMillis() + (1000 * 60 * 60));
        return "Bearer " + Jwts.builder()
                .setSubject(user.getLogin())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("id", user.getId())
                .compact();
    }

    @Override
    public User getUser() {
        return userRepository.findByLogin(jwtProvider.getLogin()).orElseThrow(() -> new AuthException("bad token"));
    }

}
