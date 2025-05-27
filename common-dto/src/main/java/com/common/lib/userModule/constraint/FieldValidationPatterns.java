package com.common.lib.userModule.constraint;

import lombok.experimental.UtilityClass;


/**
 * Utility class that defines regular expression patterns and corresponding error messages
 * for validating user input fields in the User entity.
 *
 * <p>These patterns are intended to be used with manual validation logic or integrated into
 * validation annotations.</p>
 * <p>
 * Author: Ihor Murashko
 */
@UtilityClass
public class FieldValidationPatterns {


    /**
     * Email must match standard email pattern (RFC 5322 simplified).
     */
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String EMAIL_MESSAGE = "Invalid email format";

    /**
     * Password must be at least 4 characters long and contain both letters and either digits or special characters.
     */
    public static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*[0-9!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{4,}$";
    public static final String PASSWORD_MESSAGE = "Password must be at least 4 characters long and contain letters and digits or special characters";

    /**
     * First and last names must be at least 2 characters long, consist of only Latin or Cyrillic letters,
     * and may include spaces or hyphens.
     */
    public static final String NAME_PATTERN = "^(?=.{2,}$)[A-Za-zА-Яа-яЁё]+(?:[-\\s][A-Za-zА-Яа-яЁё]+)*$";;
    public static final String NAME_MESSAGE = "Name must contain only letters (Latin or Cyrillic), be at least 2 characters, and may include spaces or hyphens";

    /**
     * Phone number must contain only digits and be 10 to 15 digits long.
     */
    public static final String PHONE_PATTERN = "^[0-9]{9,15}$";
    public static final String PHONE_MESSAGE = "Phone number must contain only digits and be 9 to 15 characters long";

    /**
     * Country, city, and street must follow the same rules as names.
     */
    public static final String ADDRESS_PATTERN = NAME_PATTERN;
    public static final String ADDRESS_MESSAGE = "Field must contain only letters (Latin or Cyrillic), be at least 2 characters, and may include spaces or hyphens";

    /**
     * House number: alphanumeric (e.g., 10, 12A, B4).
     */
    public static final String HOUSE_NUMBER_PATTERN = "^[A-Za-z0-9\\-]{1,10}$";
    public static final String HOUSE_NUMBER_MESSAGE = "House number must be alphanumeric (e.g., 10, 12A, B4)";

    /**
     * ZIP code: typically 4 to 10 alphanumeric characters.
     */
    public static final String ZIP_CODE_PATTERN = "^[A-Za-z0-9\\-]{4,10}$";
    public static final String ZIP_CODE_MESSAGE = "ZIP code must be 4 to 10 alphanumeric characters";

}

