package com.common.lib.exception;

import java.util.List;

public record BaseExceptionResponse(
        int httpStatus,
        String message,
        List<String> causes
) {
}
