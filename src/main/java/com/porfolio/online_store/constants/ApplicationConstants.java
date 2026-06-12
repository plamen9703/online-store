package com.porfolio.online_store.constants;

public class ApplicationConstants {
    //user validation messages
    public static final String USERNAME_SIZE_ERROR_MESSAGE = "Username must be between 3 and 20 characters!";
    public static final String PASSWORD_SIZE_ERROR_MESSAGE = "Password must be at least 6 characters!";
    public static final String FIRST_NAME_SIZE_ERROR_MESSAGE = "First name must be at most 20 characters!";
    public static final String LAST_NAME_SIZE_ERROR_MESSAGE = "Last name must be at most 20 characters!";
    public static final String INVALID_EMAIL_ERROR_MESSAGE = "Email must be valid!";
    public static final String PASSWORD_EMPTY_ERROR_MESSAGE = "Password must not be empty!";
    public static final String EMAIL_EMPTY_ERROR_MESSAGE = "Email must not be empty!";

    //default user properties
    public static final String DEFAULT_USER_USERNAME = "DEFAULT_USER_ADMIN_USERNAME";
    public static final String DEFAULT_USER_EMAIL = "DEFAULT_USER_ADMIN_EMAIL";
    public static final String DEFAULT_USER_PASSWORD = "DEFAULT_USER_ADMIN_PASSWORD";
    public static final String PASSWORD_PATTERN_ERROR_MESSAGE = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character!";

    //product validation messages
    public static final String PRODUCT_NAME_SIZE_ERROR_MESSAGE = "Product name must be between 3 and 50 characters!";
    public static final String PRODUCT_DESCRIPTION_ERROR_MESSAGE = "Product description must be at most 1000 characters!";
    public static final String PRODUCT_PRICE_ERROR_MESSAGE = "Product price must be a positive number!";
    public static final String PRODUCT_STOCK_QUANTITY_ERROR_MESSAGE = "Product stock quantity must be a positive number!";
}
