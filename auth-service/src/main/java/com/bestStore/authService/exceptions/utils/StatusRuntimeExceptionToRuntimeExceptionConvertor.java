package com.bestStore.authService.exceptions.utils;

import com.bestStore.authService.exceptions.ExceptionConstantMessage;
import com.bestStore.authService.exceptions.constants.ServiceRequestConstants;
import com.bestStore.authService.exceptions.exception.CredentialsException;
import com.bestStore.authService.exceptions.exception.ExternalServiceException;
import com.bestStore.authService.exceptions.exception.InternalServerErrorException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StatusRuntimeExceptionToRuntimeExceptionConvertor {

    public RuntimeException convert(StatusRuntimeException ex) {

        Status.Code code = ex.getStatus().getCode();

        return switch (code) {
            case INVALID_ARGUMENT -> new CredentialsException(
                    String.format(
                            ExceptionConstantMessage.INVALID_ARGUMENT, ex.getMessage()
                    ));
            case ALREADY_EXISTS -> new CredentialsException(
                    ExceptionConstantMessage.USER_ACCOUNT_HAS_ALREADY_EXIST
            );
            case UNAVAILABLE -> new ExternalServiceException(
                    String.format(ExceptionConstantMessage.SERVICE_UNAVAILABLE,
                            ServiceRequestConstants.USER_SERVICE)
            );
            default -> new InternalServerErrorException(
                    String.format(
                            ExceptionConstantMessage.INTERNAL_SERVER_ERROR, code.name()
                    )
            );
        };
    }
}
