package com.hits.sport.service.impl;

import com.hits.sport.model.Token;
import com.hits.sport.model.User;
import com.hits.sport.service.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmailMockServiceImpl implements EmailService {
    @Override
    public void sendEmailConfirmMessage(User user, String token) {
        String link = "http://localhost/user/confirm?token=" + token;
        String text = "<body>Для подтверждения почте перейдите по ссылке <a href=" + link + ">ссылка</a></body>";
        sendMessage(user.getEmail(), "Подтверждение почты.", text);
    }

    @Override
    public void sendEmailChangePassword(User user, String token) {
        String text = "<body>Для изменения пароля введите код" + token + "</body>";
        sendMessage(user.getEmail(), "Изменение пароля", text);
    }

    private void sendMessage(String email, String title, String text) {
        log.info("SEND: " + email + " : " + title + " : " + text);
    }
}
