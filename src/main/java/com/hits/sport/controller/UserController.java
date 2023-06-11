package com.hits.sport.controller;

import com.hits.sport.dto.common.TokenDto;
import com.hits.sport.dto.user.CredentialsDto;
import com.hits.sport.dto.user.EditUserDto;
import com.hits.sport.dto.user.RegisterDto;
import com.hits.sport.dto.user.UserDto;
import com.hits.sport.exception.ForbiddenException;
import com.hits.sport.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.hits.sport.config.SecurityConfig.X_TOKEN_FOR_API;
import static com.hits.sport.utils.Path.*;

@Api
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping(USER_REGISTER)
    void register(@Valid @RequestBody RegisterDto registerDto) {
        userService.register(registerDto);
    }

    @PostMapping(USER_SING_IN)
    TokenDto singIn(@Valid @RequestBody CredentialsDto credentialsDto) {
        return userService.singIn(credentialsDto);
    }


    @GetMapping(USER_GET_ME)
    UserDto getMe() {
        return userService.getMe();
    }

    @PostMapping(USER_PASSWORD)
    void restorePassword(@Valid @RequestParam String login) {
        userService.restorePassword(login);
    }

    @PutMapping(USER_PASSWORD_RESTORE)
    void restorePasswordToken(@Valid @RequestParam String login, @Valid @RequestParam String token, @Valid @RequestParam String password) {
        userService.restorePasswordToken(login, token, password);
    }

    @GetMapping(USER_EMAIL_CONFIRM)
    String confirmEmailToken(String token) {
        return userService.confirmEmailToken(token);
    }

    @PostMapping(USER_RESTORE_TOKEN)
    TokenDto restoreToken(String token) {
        return userService.restoreToken(token);
    }

    @PutMapping(USER_EDIT)
    UserDto editUser(@Valid @RequestBody EditUserDto editUserDto){
        return userService.editUser(editUserDto);
    }

    @PostMapping(USER_PROMOTE)
    void promoteToTrainer(String shortName){
        userService.promoteToTrainer(shortName);
    }

    @PostMapping("/user/confirm-all/")
    void confirmAll(@RequestHeader("X-Token") String XToken) {
        if(!XToken.equals(X_TOKEN_FOR_API)) {
            throw new ForbiddenException();
        }
        userService.confirmAll();
    }
}
