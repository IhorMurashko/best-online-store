package com.bestStore.userService.services.auth;

import com.bestStore.userService.exceptions.ExceptionMessageProvider;
import com.bestStore.userService.exceptions.UserNotFoundException;
import com.bestStore.userService.mapper.UserFullInfoMapper;
import com.bestStore.userService.model.User;
import com.bestStore.userService.services.userCrudService.UserCrudService;
import com.bestStore.userService.utils.UserFieldAdapter;
import com.common.lib.authModule.authDto.LoginCredentialsDto;
import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import com.common.lib.userModule.exception.InvalidAuthCredentials;
import com.common.lib.userModule.roles.Role;
import com.common.lib.userModule.userDto.response.BasicUserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserCrudService userCrudService;
    private final PasswordEncoder passwordEncoder;
    private final UserFullInfoMapper userFullInfoMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public boolean registration(@NonNull RegistrationCredentialsDto registrationCredentials, Set<Role> roles) {

        if (!registrationCredentials.password()
                .equals(registrationCredentials.confirmationPassword())) {
            log.error("Passwords do not match");

            throw new InvalidAuthCredentials(String.format(
                    ExceptionMessageProvider.PASSWORDS_DONT_MATCH));
        }

        String email = UserFieldAdapter.toLower(registrationCredentials.email());

        if (userCrudService.isEmailExist(email)) {
            log.error("Email already exist");
            throw new InvalidAuthCredentials(String.format(
                    ExceptionMessageProvider.EMAIL_ALREADY_EXIST, email
            ));
        }

        User user = new User(
                email,
                passwordEncoder.encode(registrationCredentials.password()),
                true, true, true, true,
                roles
        );

        userCrudService.save(user);

        return true;
    }

    @Override
    public BasicUserInfoResponse login(@NonNull LoginCredentialsDto loginCredentials) {

        String email = UserFieldAdapter.toLower(loginCredentials.email());

        User user = userCrudService.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(String.format(
                        ExceptionMessageProvider.USER_EMAIL_NOT_FOUND, email
                ))
        );

        boolean matches = passwordEncoder.matches(
                loginCredentials.password(), user.getPassword());

        if (matches) {

            return userFullInfoMapper.toDto(user);

        } else {
            log.error("Invalid credentials for authentication");

            throw new InvalidAuthCredentials(String.format(
                    ExceptionMessageProvider.INVALID_CREDENTIALS
            ));
        }

    }

    @Override
    public BasicUserInfoResponse getCurrentUserInfo(long userId) {

        User user = userCrudService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        ExceptionMessageProvider.USER_ID_NOT_FOUND, userId
                )));

        return userFullInfoMapper.toDto(user);

    }
}
