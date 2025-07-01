package com.beststore.userservice.controllers;


import com.beststore.userservice.exceptions.ExceptionMessageProvider;
import com.beststore.userservice.services.auth.AuthService;
import com.beststore.userservice.services.update.UpdatableUserInfoService;
import com.beststore.userservice.services.userCrudService.UserCrudServiceImpl;
import com.common.lib.authModule.authDto.BasicUserAuthenticationResponseDto;
import com.common.lib.authModule.authDto.LoginCredentialsDto;
import com.common.lib.authModule.authDto.OauthRegistrationCredentialsDto;
import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import com.common.lib.exception.RequestDoesNotContainsHeader;
import com.common.lib.headers.HeadersProvider;
import com.common.lib.userModule.roles.Role;
import com.common.lib.userModule.userDto.request.UserUpdateRequestDto;
import com.common.lib.userModule.userDto.response.BasicUserInfoResponse;
import com.common.lib.userModule.userDto.response.UserFullInfoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(
        name = "user controller",
        description = "CRUD operations for the users."
)

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Validated
public class AuthUserController {

    private final AuthService authService;
    private final UpdatableUserInfoService updatableUserInfoService;
    private final UserCrudServiceImpl userCrudService;

    @Operation(
            summary = "REGISTRATION (NOT FOR FRONTEND)",
            description = "create new user with unique email in the system.")

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "HttpStatus.CREATED",
                            description = "user was created"
                    ),
                    @ApiResponse(responseCode = "HttpStatus.BAD_REQUEST",
                            description = "invalid credentials")
            }
    )

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createUser(
            @Parameter(
                    description = "user's credentials for the registration in the system",
                    required = true)
            @RequestBody @Valid RegistrationCredentialsDto registrationCredentialsDto) {
        authService.registration(registrationCredentialsDto, Set.of(Role.ROLE_USER));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/create-oauth")
    public ResponseEntity<HttpStatus> createOauthUser(
            @Parameter(
                    description = "user's credentials for the registration in the system",
                    required = true)
            @RequestBody @Valid OauthRegistrationCredentialsDto oauthRegistrationCredentialsDto) {
        authService.oauthRegistration(oauthRegistrationCredentialsDto, Set.of(Role.ROLE_USER));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "LOGIN (NOT FOR FRONTEND)",
            description = "get user by his email and password"
    )

    @ApiResponse(
            description = "response contains object with all information about the user inside",
            responseCode = "HttpStatus.OK",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserFullInfoResponseDto.class)
            )
    )


    @PostMapping("get")
    public ResponseEntity<BasicUserInfoResponse> login(
            @Parameter(
                    description = "login user's in the system by his credentials",
                    required = true)
            @RequestBody @Valid LoginCredentialsDto loginCredentialsDto) {

        BasicUserInfoResponse login = authService.login(loginCredentialsDto);

        return ResponseEntity.ok(login);
    }

    @Operation(
            summary = "GET CURRENT USER",
            description = "get current user by his ID in the header."
    )
    @ApiResponse(
            description = "response contains object with all information about the user inside",
            responseCode = "HttpStatus.OK",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserFullInfoResponseDto.class)
            )
    )

    @GetMapping("/get")
    public ResponseEntity<BasicUserInfoResponse> getCurrentUser(
            @Parameter(
                    description = "get current user by his ID in the header."
            )

            @RequestHeader(value = HeadersProvider.USER_ID_HEADER_NAME) String userId
    ) {

        if (userId == null) {
            throw new RequestDoesNotContainsHeader(
                    String.format(
                            ExceptionMessageProvider.REQUEST_HEADER_DOES_NOT_PRESENT,
                            HeadersProvider.USER_ID_HEADER_NAME
                    )
            );
        }

        BasicUserInfoResponse currentUserInfo
                = authService.getCurrentUserInfo(Long.parseLong(userId));

        return ResponseEntity.ok(currentUserInfo);

    }

    @GetMapping("/get-by-email")
    public ResponseEntity<BasicUserAuthenticationResponseDto> getUserByEmail(
            @Parameter(
                    description = "get current user by his Email in the header."
            )

            @RequestHeader(value = HeadersProvider.USER_EMAIL_HEADER_NAME) String userEmail
    ) {

        if (userEmail == null) {
            throw new RequestDoesNotContainsHeader(
                    String.format(
                            ExceptionMessageProvider.REQUEST_HEADER_DOES_NOT_PRESENT,
                            HeadersProvider.USER_EMAIL_HEADER_NAME
                    )
            );
        }

        BasicUserAuthenticationResponseDto currentUserInfo
                = authService.getCurrentUserInfo(userEmail);

        return ResponseEntity.ok(currentUserInfo);
    }

    @GetMapping("/exist-by-email")
    public ResponseEntity<Boolean> existsByEmail(
            @Parameter(
                    description = "check if user exist by email"
            )

            @RequestHeader(value = HeadersProvider.USER_EMAIL_HEADER_NAME) String userEmail
    ) {

        if (userEmail == null) {
            throw new RequestDoesNotContainsHeader(
                    String.format(
                            ExceptionMessageProvider.REQUEST_HEADER_DOES_NOT_PRESENT,
                            HeadersProvider.USER_EMAIL_HEADER_NAME
                    )
            );
        }

        return ResponseEntity.ok(userCrudService.isEmailExist(userEmail));
    }


    @Operation(
            summary = "UPDATE INFO",
            description = "update personal info."
    )
    @ApiResponse(
            description = "response contains object with updated user's information inside",
            responseCode = "HttpStatus.OK",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserFullInfoResponseDto.class)
            )
    )
    @PatchMapping("/update")
    public ResponseEntity<BasicUserInfoResponse> updateUser(
            @Parameter(
                    description = "update personal information about user.",
                    required = true
            )

            @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto,

            @Parameter(
                    description = "user id in the system.",
                    required = true
            )
            @RequestHeader(HeadersProvider.USER_ID_HEADER_NAME) String userIdHeader) {


        if (userIdHeader == null) {
            throw new RequestDoesNotContainsHeader(
                    String.format(ExceptionMessageProvider
                                    .REQUEST_HEADER_DOES_NOT_PRESENT,
                            HeadersProvider.USER_ID_HEADER_NAME)
            );
        }


        BasicUserInfoResponse basicUserInfoResponse = updatableUserInfoService
                .updateUser(userUpdateRequestDto, Long.parseLong(userIdHeader));

        return ResponseEntity.status(HttpStatus.OK).body(basicUserInfoResponse);
    }

    @Operation(
            summary = "DELETE USER",
            description = "delete user by his ID in the header."
    )


    @ApiResponse(
            description = "response contains http status NO CONTENT",
            responseCode = "HttpStatus.NO_CONTENT"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUserById(
            @Parameter(description = "delete user by his ID in the header.",
                    required = true
            )
            @RequestHeader(HeadersProvider.USER_ID_HEADER_NAME) String userIdHeader) {


        if (userIdHeader == null) {
            throw new RequestDoesNotContainsHeader(
                    String.format(ExceptionMessageProvider
                                    .REQUEST_HEADER_DOES_NOT_PRESENT,
                            HeadersProvider.USER_ID_HEADER_NAME)
            );
        }

        updatableUserInfoService.deleteUser(Long.parseLong(userIdHeader));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
