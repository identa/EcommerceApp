package com.example.ecommerceapp.constants;

public class ValidationConst {
    public static final String EMAIL = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    public static final String PWD_ERROR = "Password doesn't matched";
    public static final String EMAIL_ERROR = "Invalid email";
    public static final String INCORRECT = "Incorrect email or password";
    public static final String PW = "^.*(?=.{8,})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+).*";
}
