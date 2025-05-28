package com.common.lib.userModule.userDto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.common.lib.userModule.constraint.FieldValidationPatterns.*;

/**
 * DTO for user profile update requests.
 * All fields are optional (except ID); only non-null values will be processed and validated.
 *
 * @author Ihor Murashko
 */
@Builder
public record UserUpdateRequestDto(

        @Pattern(regexp = EMAIL_PATTERN,
                message = EMAIL_MESSAGE)
        String email,
        @Pattern(regexp = PASSWORD_PATTERN,
                message = PASSWORD_MESSAGE)
        String password,
        @Pattern(regexp = NAME_PATTERN,
                message = NAME_MESSAGE)
        String firstName,
        @Pattern(regexp = NAME_PATTERN,
                message = NAME_MESSAGE)
        String lastName,
        @Pattern(regexp = PHONE_PATTERN,
                message = PHONE_MESSAGE)
        String phoneNumber,
        @Pattern(regexp = ADDRESS_PATTERN,
                message = ADDRESS_MESSAGE)
        String country,
        @Pattern(regexp = ADDRESS_PATTERN,
                message = ADDRESS_MESSAGE)
        String city,
        @Pattern(regexp = ADDRESS_PATTERN,
                message = ADDRESS_MESSAGE)
        String streetName,

        @Pattern(regexp = HOUSE_NUMBER_PATTERN,
                message = HOUSE_NUMBER_MESSAGE)
        String houseNumber,

        @Pattern(regexp = ZIP_CODE_PATTERN,
                message = ZIP_CODE_MESSAGE)
        String zipCode

) {
}
