package com.mmmp.netadvert.constants;

/**
 * Created by milosandric on 12/12/2016.
 */
public class UserConstants {
    public static final String NEW_FIRST_NAME = "John";
    public static final String NEW_LAST_NAME = "Doe";
    public static final String NEW_CARD_NUMBER = "X123";

    public static final Long DB_ID = 1L;
    public static final Long DB_USER_ROLE_ID = 2L;
    public static final String DB_EMAIL = "milosa942@gmail.com";
    public static final String DB_FIRST_NAME = "Milos";
    public static final String DB_LAST_NAME = "Andric";
    public static final String DB_PASSWORD = "pass";
    public static final Long DB_USER_RATE = 0L;

    public static final Long DB_FIND_ONE = 1L;

    //number of students whose last name is DB_LAST_NAME
    public static final int DB_COUNT_WITH_LAST_NAME = 2;

    //id of a student who is referenced by other entities
    public static final Long DB_ID_REFERENCED = 1L;

    //number of courses enrolled by student with ID DB_ID_REFERENCED
    public static final int DB_COUNT_STUDENT_COURSES = 2;

    //number of exams for student with ID DB_ID_REFERENCED
    public static final int DB_COUNT_STUDENT_EXAMS = 2;

    public static final int DB_COUNT = 3;
    public static final int PAGE_SIZE = 5;
}
