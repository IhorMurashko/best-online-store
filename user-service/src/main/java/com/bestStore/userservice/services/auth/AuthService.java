package com.bestStore.userservice.services.auth;

import com.common.lib.authModule.authDto.BasicUserAuthenticationResponseDto;
import com.common.lib.authModule.authDto.LoginCredentialsDto;
import com.common.lib.authModule.authDto.OauthRegistrationCredentialsDto;
import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import com.common.lib.userModule.roles.Role;
import com.common.lib.userModule.userDto.response.BasicUserInfoResponse;
import org.springframework.lang.NonNull;

import java.util.Set;

public interface AuthService {

    boolean registration(@NonNull RegistrationCredentialsDto registrationCredentialsDto, Set<Role> roles);

    boolean oauthRegistration(@NonNull OauthRegistrationCredentialsDto oauthRegistrationCredentialsDto, Set<Role> roles);

    BasicUserInfoResponse login(@NonNull LoginCredentialsDto loginCredentialsDto);

    BasicUserInfoResponse getCurrentUserInfo(long userId);

    BasicUserAuthenticationResponseDto getCurrentUserInfo(String email);

}
