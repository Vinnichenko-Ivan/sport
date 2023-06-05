package com.hits.sport.service.impl;

import com.hits.sport.model.User;
import com.hits.sport.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.MissingFormatArgumentException;

import static com.hits.sport.utils.Path.USER_EMAIL_CONFIRM;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Override
    public void sendEmailConfirmMessage(User user, String token) {
        String link = "http://localhost:8080/" + USER_EMAIL_CONFIRM + "?token=" + token;
        String text = "<body>Для подтверждения почте перейдите по ссылке <a href=" + link + ">ссылка</a></body>";
        sendMessage(user.getEmail(), "Подтверждение почты.", text);
    }

    @Override
    public void sendEmailChangePassword(User user, String token) {
        String text = "<body>Для изменения пароля введите код" + token + "</body>";
        sendMessage(user.getEmail(), "Изменение пароля", text);
    }

    private void sendMessage(String email, String title, String text) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@sport.com");
            helper.setTo(email);
            helper.setSubject(title);
            helper.setText(text);
            emailSender.send(message);
            log.info("SEND: " + email + " : " + title + " : " + text);
        } catch (MessagingException e){

        }

    }
}
