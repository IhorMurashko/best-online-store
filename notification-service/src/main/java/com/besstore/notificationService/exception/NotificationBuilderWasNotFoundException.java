package com.besstore.notificationService.exception;


import com.besstore.notificationService.utils.contentBuilder.NotificationContentBuilder;
import com.common.lib.notificationModule.request.constants.NotificationChannel;
import com.common.lib.notificationModule.request.constants.NotificationType;

/**
 * Exception thrown when a notification builder is not found for the specified
 * notification type and channel. This typically occurs when no matching
 * {@link NotificationContentBuilder} is registered in the application context
 * for the provided {@link NotificationType} and {@link NotificationChannel}.
 *
 * The exception provides a message detailing the missing builder's type and
 * channel, which can be formatted using the predefined template in
 * {@link ExceptionMessages#NOTIFICATION_BUILDER_WAS_NOT_FOUND}.
 *
 * Example scenarios where this exception might be thrown include:
 * - Attempting to retrieve a builder for a notification type and channel
 *   combination that has not been implemented or registered.
 * - Errors in configuration or application context setup where the
 *   appropriate beans are not correctly initialized.
 *
 * This exception is generally used within the notification service to indicate
 * that the application is unable to process a notification due to a missing
 * builder.
 *
 * @author Ihor Murashko
 */
public class NotificationBuilderWasNotFoundException extends RuntimeException {
    public NotificationBuilderWasNotFoundException(String message) {
        super(message);
    }
}
