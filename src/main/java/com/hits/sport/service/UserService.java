package com.hits.sport.service;

import com.hits.sport.dto.common.TokenDto;
import com.hits.sport.dto.user.CredentialsDto;
import com.hits.sport.dto.user.EditUserDto;
import com.hits.sport.dto.user.RegisterDto;
import com.hits.sport.dto.user.UserDto;

public interface UserService {

    void register(RegisterDto registerDto);

    TokenDto singIn(CredentialsDto credentialsDto);

    UserDto getMe();

    void restorePassword(String login);

    void restorePasswordToken(String login, String token, String password);

    String confirmEmailToken(String token);

    TokenDto restoreToken(String token);

    UserDto editUser(EditUserDto editUserDto);

    void promoteToTrainer(String shortName);

    void confirmAll();
}
