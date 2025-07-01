package com.beststore.rest.accessHandling;


import com.bestStore.core.exceptions.ExceptionMessageConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class RestAuthEntryPoint implements AuthenticationEntryPoint {
    private final Logger log = LoggerFactory.getLogger(RestAuthEntryPoint.class);

    /**
     * Commences an authentication scheme.
     * <p>
     * This method is called when an exception is thrown due to an unauthenticated user attempting to access a secured resource.
     * It logs the error and sends a 401 Unauthorized response to the client.
     *
     * @param request       the HttpServletRequest in which the exception occurred.
     * @param response      the HttpServletResponse to which the error response will be sent.
     * @param authException the exception that caused the invocation.
     * @throws IOException      if an input or output exception occurs.
     * @throws ServletException if a servlet-specific error occurs.
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        log.error(authException.getMessage(), authException);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(ExceptionMessageConstants.UNAUTHORIZED_EXCEPTION_MESSAGE);
    }
}

