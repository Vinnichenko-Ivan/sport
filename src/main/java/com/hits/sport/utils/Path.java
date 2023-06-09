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
    public static final String TRAINER_USERS = TRAINER_URL + "users";
    public static final String GROUP_URL = "/group/";

    public static final String GROUPS_MY_URL = "/my/groups/";
    public static final String GROUPS_TRAINING_URL = "/training/groups/";
    public static final String GROUP_EDIT_URL = GROUP_URL + "{groupId}";

    public static final String GROUP_USERS = GROUP_URL + "{groupId}/users";

    public static final String GROUP_TRAINERS = GROUP_URL + "{groupId}/trainers";

    public static final String EXERCISE_URL = "/exercise/";
    public static final String EXERCISE_GET = EXERCISE_URL + "{exerciseId}";
    public static final String EXERCISES_URL = "/exercises/";
}
