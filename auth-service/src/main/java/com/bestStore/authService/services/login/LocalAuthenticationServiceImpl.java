package com.bestStore.authService.services.login;

import com.bestStore.ByEmail;
import com.bestStore.UserCredentials;
import com.bestStore.auth.UserFinderServiceGrpc;
import com.bestStore.authService.constatnts.GrpcClientConstant;
import com.bestStore.authService.exceptions.ExceptionConstantMessage;
import com.bestStore.authService.exceptions.exception.CredentialsException;
import com.bestStore.authService.exceptions.exception.UserAccountIsNotAvailableException;
import com.bestStore.authService.exceptions.exception.UserNotFoundException;
import com.bestStore.authService.exceptions.utils.StatusRuntimeExceptionToRuntimeExceptionConvertor;
import com.bestStore.core.constants.TokenClaimsConstants;
import com.bestStore.core.constants.TokenType;
import com.bestStore.core.jwtProvider.JwtTokenProvider;
import com.beststore.rest.utils.RolesParserUtil;
import com.common.lib.authModule.authDto.LoginCredentialsDto;
import com.common.lib.authModule.token.TokenDto;
import io.grpc.StatusRuntimeException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
public class LocalAuthenticationServiceImpl implements AuthenticationService {

    @Value("${accessTokenAvailableValidityPeriodInSec}")
    private Long accessTokenAvailableValidityPeriodInSec;

    @Value("${refreshTokenAvailableValidityPeriodInSec}")
    private Long refreshTokenAvailableValidityPeriodInSec;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @GrpcClient(value = GrpcClientConstant.USER_GRPC_CLIENT_NAME)
    private UserFinderServiceGrpc.UserFinderServiceBlockingStub userFinderServiceBlockingStub;

    private UserFinderServiceGrpc.UserFinderServiceBlockingStub stub() {
        return userFinderServiceBlockingStub.withDeadlineAfter(5, TimeUnit.SECONDS);
    }

    @Override
    public TokenDto login(@NonNull @Valid LoginCredentialsDto loginCredentialsDto) {

        UserCredentials credentials;

        try {
            ByEmail userByEmail = ByEmail.newBuilder()
                    .setEmail(loginCredentialsDto.email().toLowerCase())
                    .build();

            credentials = stub().getByEmail(userByEmail);
            log.debug("Login grpc request to user service was sent successfully with email: {}",
                    loginCredentialsDto.email());

            if (credentials == null) {
                log.warn("user wasn't found with email {}", loginCredentialsDto.email());
                throw new UserNotFoundException(
                        String.format(
                                ExceptionConstantMessage.USER_NOT_FOUND, "email", userByEmail.getEmail()
                        )
                );
            }

        } catch (StatusRuntimeException ex) {
            log.error("gRPC error during login: status={}, description={}",
                    ex.getStatus().getCode(), ex.getStatus().getDescription(), ex);


            throw StatusRuntimeExceptionToRuntimeExceptionConvertor
                    .convert(ex);
        }

        if (credentials.getPassword() == null || credentials.getPassword().isEmpty()) {
            throw new CredentialsException(
                    ExceptionConstantMessage.USER_PASSWORD_IS_EMPTY
            );
        }

        if (!passwordEncoder.matches(loginCredentialsDto.password(), credentials.getPassword())) {
            log.warn("Passwords do not match");
            throw new CredentialsException(
                    ExceptionConstantMessage.PASSWORDS_DONT_MATCH
            );
        }

        if (!credentials.getIsAccountNonExpired()
                || !credentials.getIsAccountNonLocked()
                || !credentials.getIsCredentialsNonExpired()
                || !credentials.getIsEnabled()) {

            log.warn("account non expired {}", credentials.getIsAccountNonExpired());
            log.warn("account non locked {}", credentials.getIsAccountNonLocked());
            log.warn("account credentials non expired {}", credentials.getIsCredentialsNonExpired());
            log.warn("account credentials is enabled {}", credentials.getIsEnabled());

            log.warn("account is expired for user: {}", loginCredentialsDto.email());

            throw new UserAccountIsNotAvailableException(
                    ExceptionConstantMessage.USER_ACCOUNT_IS_EXPIRED);
        }

        Map<String, Object> claims = Map.of(
                TokenClaimsConstants.USER_ID_CLAIM, credentials.getId(),
                TokenClaimsConstants.USERNAME_CLAIM, credentials.getEmail(),
                TokenClaimsConstants.ROLES_CLAIM,
                RolesParserUtil.getRolesListFromList(credentials.getRolesList()
                ));

        log.debug("claims was created");

        String accessToken = jwtTokenProvider.generateToken(
                credentials.getId(),
                accessTokenAvailableValidityPeriodInSec,
                claims,
                TokenType.ACCESS);
        log.debug("accessToken was created");

        String refreshTokenToken = jwtTokenProvider.generateToken(
                credentials.getId(),
                refreshTokenAvailableValidityPeriodInSec,
                claims,
                TokenType.REFRESH);
        log.debug("refreshToken was created");

        return new TokenDto(accessToken, refreshTokenToken);
    }
}
