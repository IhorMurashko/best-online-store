package com.besstore.notificationService.controllers;


import com.besstore.notificationService.service.DispatcherService;
import com.common.lib.exception.BaseExceptionResponse;
import com.common.lib.notificationModule.request.notification.DefaultNotification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for receiving and dispatching notification requests.
 *
 * <p>This is the main entry point for external clients. Accepts notification DTOs and delegates
 * processing to the dispatcher service.
 *
 * @author Ihor Murashko
 */
@Tag(
        name = "Notification Controller",
        description = "Handles notification delivery requests across different channels and types."
)
@RestController
@RequestMapping("api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final DispatcherService dispatcherService;

    /**
     * Handles the processing of a notification request and triggers the dispatcher service
     * to send the notification using the given details.
     *
     * @param notification the notification details containing type, channel, recipient identifier,
     *                     optional subject, and optional dynamic data for template processing.
     *                     Must not be null.
     * @return a ResponseEntity with HTTP status 200 if the notification was sent successfully,
     *         or appropriate error codes such as 400 for bad requests and 500 for server errors.
     */
    @Operation(
            summary = "Send Notification",
            description = "Processes a request to send a notification using the specified type, channel, and recipient details."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification was sent successfully."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request format or missing required fields.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BaseExceptionResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Server error while processing the notification.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BaseExceptionResponse.class)
                    )
            )
    })

    /**
     * Receives notification request and delegates processing to the dispatcher service.
     * @param notification notification details
     */
    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(
            @Parameter(description = "notification details", required = true)
            @RequestBody @NonNull DefaultNotification notification) {

        dispatcherService.send(notification);

        return ResponseEntity.ok().build();
    }
}
