package com.beststore.userservice.services.auth;

import com.beststore.userservice.exceptions.ExceptionMessageProvider;
import com.beststore.userservice.exceptions.UserNotFoundException;
import com.beststore.userservice.mapper.UserFullInfoMapper;
import com.beststore.userservice.model.User;
import com.beststore.userservice.services.userCrudService.UserCrudService;
import com.beststore.userservice.utils.UserFieldAdapter;
import com.common.lib.authModule.authDto.BasicUserAuthenticationResponseDto;
import com.common.lib.authModule.authDto.LoginCredentialsDto;
import com.common.lib.authModule.authDto.OauthRegistrationCredentialsDto;
import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import com.common.lib.exception.InvalidAuthCredentials;
import com.common.lib.userModule.AuthProvider.AuthProvider;
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

//        if (!registrationCredentials.password()
//                .equals(registrationCredentials.confirmationPassword())) {
//            log.error("Passwords do not match");
//
//            throw new InvalidAuthCredentials(String.format(
//                    ExceptionMessageProvider.PASSWORDS_DONT_MATCH));
//        }

//        String email = UserFieldAdapter.toLower(registrationCredentials.email());

        if (userCrudService.isEmailExist(registrationCredentials.email())) {
            log.error("Email already exist");
            throw new InvalidAuthCredentials(String.format(
                    ExceptionMessageProvider.EMAIL_ALREADY_EXIST, registrationCredentials.email()
            ));
        }

        User user = new User(
                registrationCredentials.email(),
                registrationCredentials.password(),
                AuthProvider.LOCAL,
                null,
                true, true, true, true,
                roles
        );

        userCrudService.save(user);

        return true;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public boolean oauthRegistration(OauthRegistrationCredentialsDto oauthRegistrationCredentialsDto, Set<Role> roles) {

        if (userCrudService.isEmailExist(oauthRegistrationCredentialsDto.email())) {
            log.error("Email already exist");
            throw new InvalidAuthCredentials(String.format(
                    ExceptionMessageProvider.EMAIL_ALREADY_EXIST, oauthRegistrationCredentialsDto.email()
            ));
        }
        System.out.println(oauthRegistrationCredentialsDto.oauthId());
        System.out.println(oauthRegistrationCredentialsDto.oauthProvider());
        User user = new User(
                oauthRegistrationCredentialsDto.email(),
                null,
                oauthRegistrationCredentialsDto.oauthProvider(),
                oauthRegistrationCredentialsDto.oauthId(),
                true, true, true, true,
                roles
        );

        String fullName = oauthRegistrationCredentialsDto.fullName();
        String[] firstAndLastName = fullName.split(" ", 2);

        user.setFirstName(firstAndLastName[0]);
        user.setLastName(firstAndLastName.length > 1 ? firstAndLastName[1] : "");

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

    @Override
    public BasicUserAuthenticationResponseDto getCurrentUserInfo(String email) {

        User user = userCrudService.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        ExceptionMessageProvider.USER_EMAIL_NOT_FOUND, email
                )));

        return new BasicUserAuthenticationResponseDto(user.getId(), user.getEmail(), user.getPassword(), user.getRoles());
    }
}
