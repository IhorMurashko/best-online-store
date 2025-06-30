package com.beststore.rest.utils;

import com.bestStore.core.exceptions.ExceptionMessageConstants;
import com.bestStore.core.exceptions.headerException.HeadersRequestException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * Utility class for validating and extracting HTTP headers from a request.
 *
 * <p>
 * Designed to enforce presence of required headers during authentication or authorization flows.
 * Throws {@link HeadersRequestException} when a required header is missing or blank.
 * </p>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 */
public class HeaderUtils {

    private final Logger log = LoggerFactory.getLogger(HeaderUtils.class);

    private HeaderUtils() {

    }

    public Map<String, String> checkHttpRequestHeaders(@NonNull HttpServletRequest request,
                                                       @NonNull String... requiredHeaders) {

        return Arrays.stream(requiredHeaders)
                .map(headerName -> {
                    String headerValue = request.getHeader(headerName);
                    if (headerValue == null || headerValue.isBlank()) {
                        log.warn("Required header '{}' is missing, null, or blank.", headerName);
                        throw new HeadersRequestException(
                                String.format(
                                        ExceptionMessageConstants
                                                .REQUEST_DOESNT_HAVE_REQUIRED_HEADER, headerName)
                        );
                    }
                    return Map.entry(headerName, headerValue);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}