package com.hits.sport.utils;

public class Path {
    public static final String USER_URL = "/user/";
    public static final String USER_REGISTER = USER_URL + "register/";
    public static final String USER_SING_IN = USER_URL + "sing-in/";
    public static final String USER_GET_ME = USER_URL + "me/";
    public static final String USER_PASSWORD = USER_URL + "password/";
    public static final String USER_PASSWORD_RESTORE = USER_URL + "password/";
    public static final String USER_EMAIL_CONFIRM = USER_URL + "email/";
    public static final String USER_RESTORE_TOKEN = USER_URL + "token/";
    public static final String USER_EDIT = USER_URL;
    public static final String USER_PROMOTE = USER_URL + "promote/";

    public static final String QUERY = "query/";

    public static final String TRAINER_URL = "/trainer/";
    public static final String TRAINER_GET = TRAINER_URL;

    public static final String TRAINER_ADD_QUERY = TRAINER_URL + "{trainerId}";
    public static final String TRAINER_MY_QUERY = TRAINER_URL + QUERY + "/my/";
    public static final String TRAINER_QUERY_ACCEPT = TRAINER_URL + QUERY + "{queryId}";
    public static final String TRAINER_QUERY_REJECT = TRAINER_URL + QUERY + "{queryId}";
}
