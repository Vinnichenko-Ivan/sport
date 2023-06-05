package com.hits.sport.controller;

import com.hits.sport.dto.CredentialsDto;
import com.hits.sport.dto.RegisterDto;
import com.hits.sport.dto.TokenDto;
import com.hits.sport.dto.UserDto;
import com.hits.sport.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.hits.sport.utils.Path.*;

@Api
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping(USER_REGISTER)
    void register(@RequestBody RegisterDto registerDto) {
        userService.register(registerDto);
    }

    @PostMapping(USER_SING_IN)
    TokenDto singIn(@RequestBody CredentialsDto credentialsDto) {
        return userService.singIn(credentialsDto);
    }


    @GetMapping(USER_GET_ME)
    UserDto getMe() {
        return userService.getMe();
    }

    @PostMapping(USER_PASSWORD)
    void restorePassword(@RequestBody String login) {
        userService.restorePassword(login);
    }

    @PutMapping(USER_PASSWORD_RESTORE)
    void restorePasswordToken(String login, String token, String password) {
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
}
