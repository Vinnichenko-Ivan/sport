package com.hits.sport.service.impl;

import com.hits.sport.dto.common.TokenDto;
import com.hits.sport.dto.user.CredentialsDto;
import com.hits.sport.dto.user.EditUserDto;
import com.hits.sport.dto.user.RegisterDto;
import com.hits.sport.dto.user.UserDto;
import com.hits.sport.exception.AuthException;
import com.hits.sport.exception.BadRequestException;
import com.hits.sport.mapper.UserMapper;
import com.hits.sport.model.token.TokenType;
import com.hits.sport.model.Trainer;
import com.hits.sport.model.User;
import com.hits.sport.repository.UserRepository;
import com.hits.sport.service.*;
import com.hits.sport.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final UserMapper userMapper;

    private final JwtProvider jwtProvider;
    private final JwtService jwtService;
    private final PasswordService passwordService;

    @Override
    @Transactional
    public void register(RegisterDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BadRequestException("email already used");
        }
        if(userRepository.existsByLogin(registerDto.getLogin())) {
            throw new BadRequestException("login already used");
        }
        User user = userMapper.map(registerDto);
        user = userRepository.save(user);
        user.setPassword(passwordService.toHash(registerDto.getPassword()));
        String token = tokenService.generateToken(user, TokenType.CONFIRM);
        emailService.sendEmailConfirmMessage(user, token);
    }

    @Override
    @Transactional
    public TokenDto singIn(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin()).orElseThrow(()->new AuthException("bad login or password"));
        if(!user.getConfirm()) {
            throw new BadRequestException("not confirmed");
        }
        if(!passwordService.comparison(credentialsDto.getPassword(), user.getPassword())) {
            throw new AuthException("bad login or password");
        }
        String accessToken = jwtService.generateAccessToken(user);
        String restoreToken = tokenService.generateToken(user, TokenType.RESTORE);
        return new TokenDto(restoreToken, accessToken);
    }

    @Override
    public UserDto getMe() {
        User user = jwtProvider.getUser();
        UserDto dto = userMapper.map(user);
        dto.setIsTrainer(user.getTrainer()!=null);
        return dto;
    }

    @Override
    public void restorePassword(String login) {
        User user = userRepository.findByLogin(login).orElse(null);
        if(user != null) {
            String token = tokenService.generateToken(user, TokenType.PASSWORD_CHANGE);
            emailService.sendEmailChangePassword(user, token);
        }
    }

    @Override
    public void restorePasswordToken(String login, String token, String password) {
        User user = userRepository.findByLogin(login).orElseThrow(()->new BadRequestException("bad token"));
        try {
            User get = tokenService.getByToken(token, TokenType.PASSWORD_CHANGE, true);
            get.setPassword(passwordService.toHash(password));
            userRepository.save(get);
        } catch (BadRequestException e) {
            tokenService.deleteToken(user, TokenType.PASSWORD_CHANGE);
            throw e;
        }
    }

    @Override
    @Transactional
    public String confirmEmailToken(String token) {
        try{
            User user = tokenService.getByToken(token, TokenType.CONFIRM, true);
            user.setConfirm(true);
            userRepository.save(user);
        } catch (Exception e) {
            return "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><title>Email confirm</title></head><body style=\"background: linear-gradient(90deg, #505b8b,#60769a,#828282); display: flex;justify-content: center;align-items: center;height: 100vh;\"><main><h1 style=\"text-align: center; color: antiquewhite\">Почта не подтверждена! Токен не верен.</h1></main></body></html>";
        }
        return "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><title>Email confirm</title></head><body style=\"background: linear-gradient(90deg, #505b8b,#60769a,#828282); display: flex;justify-content: center;align-items: center;height: 100vh;\"><main><h1 style=\"text-align: center; color: antiquewhite\">Почта подтверждена!</h1></main></body></html>";
    }

    @Override
    public TokenDto restoreToken(String token) {
        User user = tokenService.getByToken(token, TokenType.RESTORE, true);
        String accessToken = jwtService.generateAccessToken(user);
        String restoreToken = tokenService.generateToken(user, TokenType.RESTORE);
        return new TokenDto(restoreToken, accessToken);
    }

    @Override
    public UserDto editUser(EditUserDto editUserDto) {
        User user = jwtProvider.getUser();
        if(!user.getEmail().equals(editUserDto.getEmail()) && userRepository.existsByEmail(editUserDto.getEmail()))
        {
            throw new BadRequestException("email already used");
        }
        user.setEmail(editUserDto.getEmail());
        user.setName(editUserDto.getName());
        user = userRepository.save(user);
        UserDto dto = userMapper.map(user);
        dto.setIsTrainer(user.getTrainer()!=null);
        return dto;
    }

    @Override
    public void promoteToTrainer(String shortName) {
        User user = jwtProvider.getUser();
        if(user.getTrainer() != null) {
            throw new BadRequestException("already trainer");
        }
        Trainer trainer = new Trainer(user);
        trainer.setShortName(shortName);
        user.setTrainer(trainer);
        userRepository.save(user);
    }

    @Override
    public void confirmAll() {
        userRepository.saveAll(
                userRepository.findAll().stream().peek(
                        user -> user.setConfirm(true)
                ).collect(Collectors.toList())
        );
    }
}
