package com.hits.sport.service;

import com.hits.sport.model.Token;
import com.hits.sport.model.User;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void sendEmailConfirmMessage(User user, String token);

    @Async
    void sendEmailChangePassword(User user, String token);
}
