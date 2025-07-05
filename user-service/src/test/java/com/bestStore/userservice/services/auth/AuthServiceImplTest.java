package com.bestStore.userservice.services.auth;

import com.bestStore.userservice.exceptions.ExceptionMessageProvider;
import com.bestStore.userservice.exceptions.UserNotFoundException;
import com.bestStore.userservice.mapper.UserFullInfoMapper;
import com.bestStore.userservice.model.User;
import com.bestStore.userservice.services.userCrudService.UserCrudService;
import com.common.lib.authModule.authDto.LoginCredentialsDto;
import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import com.common.lib.exception.InvalidAuthCredentials;
import com.common.lib.userModule.AuthProvider.AuthProvider;
import com.common.lib.userModule.roles.Role;
import com.common.lib.userModule.userDto.response.BasicUserInfoResponse;
import com.common.lib.userModule.userDto.response.BasicUserInfoResponseDto;
import com.common.lib.userModule.userDto.response.UserFullInfoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserCrudService userCrudService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserFullInfoMapper userFullInfoMapper;
    @InjectMocks
    private AuthServiceImpl authService;


    @Captor
    private ArgumentCaptor<String> emailCaptor;
    @Captor
    private ArgumentCaptor<String> passwordCaptor;


    private RegistrationCredentialsDto passwordsDontMatchRegistrationCredentialsDto;
    private RegistrationCredentialsDto validRegistrationCredentialsDto;
    private Set<Role> roles;

    private LoginCredentialsDto loginCredentialsDto;

    private User user;
    private String userEmail;
    private String userPassword;
    private UserFullInfoResponseDto userFullInfoResponseDto;
    private long userId;

    @BeforeEach
    void setUp() {

        this.passwordsDontMatchRegistrationCredentialsDto = new RegistrationCredentialsDto(
                "email@email.com",
                "pass5",
                "pass6");

        this.validRegistrationCredentialsDto = new RegistrationCredentialsDto(
                "email@email.com",
                "pass5",
                "pass5"
        );


        this.roles = Set.of(Role.ROLE_USER);
        this.userId = 1L;

        this.userEmail = String.valueOf("my@email.com");
        this.userPassword = "pass25";

        this.loginCredentialsDto = new LoginCredentialsDto(
                userEmail,
                userPassword
        );
        this.user = new User(userEmail, userPassword, AuthProvider.LOCAL, null, true, true,
                true, true, roles);


        this.userFullInfoResponseDto = new UserFullInfoResponseDto(
                new BasicUserInfoResponseDto(
                        userId, userEmail, roles, true, true,
                        true, true
                ), LocalDateTime.now(), LocalDateTime.now(), null, null,
                null, null, null, null, null, null


        );
    }


    @Test
    @DisplayName("registration_passwordsDontMatches")
    void getInvalidAuthCredentialsException_WhenPasswordsDontMatcher() {


        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.registration(this.passwordsDontMatchRegistrationCredentialsDto, this.roles));


        assertEquals(InvalidAuthCredentials.class, exception.getClass());
        assertEquals(ExceptionMessageProvider.PASSWORDS_DONT_MATCH, exception.getMessage());


        verifyNoInteractions(userCrudService);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    @DisplayName("registration_emailAlreadyExist")
    void getInvalidAuthCredentialsException_WhenEmailAlreadyExist() {

        doReturn(true).when(userCrudService).isEmailExist(anyString());

        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> authService.registration(
                        this.validRegistrationCredentialsDto, this.roles)
        );


        assertEquals(InvalidAuthCredentials.class, exception.getClass());
        assertEquals(
                String.format(
                        ExceptionMessageProvider.EMAIL_ALREADY_EXIST, validRegistrationCredentialsDto.email()),
                exception.getMessage());

        verify(userCrudService).isEmailExist(emailCaptor.capture());
        assertEquals(emailCaptor.getValue(), validRegistrationCredentialsDto.email());

        verify(userCrudService, times(1)).isEmailExist(anyString());
        verifyNoMoreInteractions(userCrudService);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    @DisplayName("registration_validRegistrationCredentials")
    void getTrue_whenValidRegistrationCredentials() {

        doReturn(false).when(userCrudService).isEmailExist(anyString());

        boolean registration = authService.registration(this.validRegistrationCredentialsDto, this.roles);

        assertTrue(registration);

        verify(userCrudService).isEmailExist(emailCaptor.capture());
        assertEquals(emailCaptor.getValue(), validRegistrationCredentialsDto.email());

        verify(passwordEncoder).encode(passwordCaptor.capture());
        assertEquals(passwordCaptor.getValue(), validRegistrationCredentialsDto.password());

        verify(userCrudService, times(1)).isEmailExist(anyString());
        verify(userCrudService, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(anyString());
        verifyNoMoreInteractions(userCrudService);
        verifyNoMoreInteractions(passwordEncoder);

    }

    @Test
    @DisplayName("login_EmailNotFound")
    void loginEmailNotFound_whenEmailNotFoundInTheDB() {

        doReturn(Optional.empty()).when(userCrudService).findByEmail(anyString());


        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                authService.login(loginCredentialsDto));


        assertNotNull(exception);
        assertEquals(UserNotFoundException.class, exception.getClass());
        assertEquals(String.format(
                ExceptionMessageProvider.USER_EMAIL_NOT_FOUND, loginCredentialsDto.email().toLowerCase()
        ), exception.getMessage());

        verify(userCrudService, times(1)).findByEmail(anyString());
        verifyNoMoreInteractions(userCrudService);
        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(userFullInfoMapper);

        verify(userCrudService).findByEmail(emailCaptor.capture());
        assertEquals(emailCaptor.getValue(), loginCredentialsDto.email().toLowerCase());

    }

    @Test
    @DisplayName("login_passwordsDontMatch")
    void getInvalidAuthCredentialsException_WhenPasswordsDontMatch() {

        doReturn(Optional.of(user)).when(userCrudService).findByEmail(loginCredentialsDto.email());
        doReturn(false).when(passwordEncoder).matches(anyString(), anyString());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                authService.login(loginCredentialsDto));


        assertNotNull(exception);
        assertEquals(InvalidAuthCredentials.class, exception.getClass());
        assertEquals(ExceptionMessageProvider.INVALID_CREDENTIALS, exception.getMessage());

        verify(userCrudService).findByEmail(emailCaptor.capture());
        assertEquals(emailCaptor.getValue(), loginCredentialsDto.email());

        verify(userCrudService, times(1)).findByEmail(anyString());
        verifyNoMoreInteractions(userCrudService);
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());

    }


    @Test
    @DisplayName("login_getUser_WhenCredentialsAreOk")
    void getUser_whenCredentialsAreOk() {

        doReturn(Optional.of(user)).when(userCrudService).findByEmail(loginCredentialsDto.email());
        doReturn(true).when(passwordEncoder).matches(anyString(), anyString());
        doReturn(userFullInfoResponseDto).when(userFullInfoMapper).toDto(user);

        BasicUserInfoResponse login = authService.login(loginCredentialsDto);

        assertNotNull(login);
        assertEquals(userFullInfoResponseDto, login);

        verify(userCrudService).findByEmail(emailCaptor.capture());
        assertEquals(emailCaptor.getValue(), loginCredentialsDto.email());


        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        verifyNoMoreInteractions(passwordEncoder);
        verify(userCrudService, times(1)).findByEmail(anyString());
        verifyNoMoreInteractions(userCrudService);
        verify(userFullInfoMapper, times(1)).toDto(user);
        verifyNoMoreInteractions(userFullInfoMapper);
    }

    @Test
    @DisplayName("getCurrentUser_UserIdNotFound")
    void getUserIdNotFound_whenUserIdNotFoundInTheDB() {

        doReturn(Optional.empty()).when(userCrudService).findById(anyLong());


        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                authService.getCurrentUserInfo(userId));


        assertNotNull(exception);
        assertEquals(UserNotFoundException.class, exception.getClass());
        assertEquals(String.format(
                ExceptionMessageProvider.USER_ID_NOT_FOUND, userId), exception.getMessage());

        verify(userCrudService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userCrudService);
        verifyNoInteractions(userFullInfoMapper);
    }

    @Test
    @DisplayName("getCurrentUser_getUser")
    void getCurrentUser_getUser() {

        doReturn(Optional.of(user)).when(userCrudService).findById(userId);
        doReturn(userFullInfoResponseDto).when(userFullInfoMapper).toDto(user);

        BasicUserInfoResponse currentUserInfo = authService.getCurrentUserInfo(userId);

        assertNotNull(currentUserInfo);
        assertEquals(userFullInfoResponseDto, currentUserInfo);
        verify(userCrudService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userCrudService);
        verify(userFullInfoMapper, times(1)).toDto(user);
        verifyNoMoreInteractions(userFullInfoMapper);


    }


}