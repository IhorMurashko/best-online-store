package com.common.lib.authModule.authDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.lang.NonNull;

import static com.common.lib.userModule.constraint.FieldValidationPatterns.*;
/**
 * DTO used for user registration.
 * Contains required fields to create a new user account.
 *
 * @author Ihor Murashko
 */
public record RegistrationCredentialsDto(


        @NonNull
        @Email(regexp = EMAIL_PATTERN,
                message = EMAIL_MESSAGE)
        String email,

        @NonNull
        @Pattern(regexp = PASSWORD_PATTERN,
                message = PASSWORD_MESSAGE)
        String password,

        @NonNull
        String confirmationPassword
) {
}
