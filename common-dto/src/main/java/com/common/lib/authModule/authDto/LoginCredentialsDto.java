package com.common.lib.authModule.authDto;

import org.springframework.lang.NonNull;
/**
 * DTO used for login requests.
 * Contains email and password.
 *
 * @author Ihor Murashko
 */
public record LoginCredentialsDto(
        @NonNull
        String email,

        @NonNull
        String password

) {

}
