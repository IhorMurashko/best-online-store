package com.common.lib.exception;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@UtilityClass
public class ResponseExceptionProvider {


    public ResponseEntity<BaseExceptionResponse> buildResponse(HttpStatus status, String message, List<String> causes) {
        BaseExceptionResponse response = new BaseExceptionResponse(
                status.value(),
                message,
                causes
        );
        return new ResponseEntity<>(response, status);
    }


}
