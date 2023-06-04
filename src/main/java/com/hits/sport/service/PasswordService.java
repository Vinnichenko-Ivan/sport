package com.hits.sport.service;

public interface PasswordService {
    String toHash(String password);

    Boolean comparison(String password, String hash);

}
