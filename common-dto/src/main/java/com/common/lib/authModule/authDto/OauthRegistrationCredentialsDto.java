package com.common.lib.authModule.authDto;

import com.common.lib.userModule.AuthProvider.AuthProvider;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jdk.jfr.Name;
import org.springframework.lang.NonNull;

import static com.common.lib.userModule.constraint.FieldValidationPatterns.*;

/**
 * DTO used for user registration with Oauth2.
 * Contains required fields to create a new user account.
 *
 * @author Matfei Hasych
 */
public record OauthRegistrationCredentialsDto(


        @NonNull
        @Email(regexp = EMAIL_PATTERN,
                message = EMAIL_MESSAGE)
        String email,

        @NonNull
        @Pattern(regexp = NAME_PATTERN,
                message = NAME_MESSAGE)
        String fullName,

        @NonNull
        String oauthId,

        @NonNull
        AuthProvider oauthProvider
) {
}
