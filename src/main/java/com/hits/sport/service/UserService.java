package com.hits.sport.service;

import com.hits.sport.dto.*;

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
}
