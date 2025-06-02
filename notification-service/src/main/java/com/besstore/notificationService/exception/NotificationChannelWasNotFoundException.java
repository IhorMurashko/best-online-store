package com.besstore.notificationService.exception;

/**
 * Exception thrown when the requested notification channel is not found.
 *
 * This exception is typically used within the Notification Service when a specific
 * notification channel cannot be resolved or is absent in the current context.
 * It usually indicates a logical error where a channel that was expected to be
 * configured or available does not exist.
 *
 * The exception message can be dynamically formatted using predefined templates
 * available in {@link ExceptionMessages#NOTIFICATION_CHANNEL_WAS_NOT_FOUND}.
 *
 * Usage scenarios include:
 * - Attempting to retrieve a notification sender for a channel that is not registered.
 * - Misconfiguration or absence of a required notification channel in the application setup.
 *
 * This exception is primarily utilized to communicate errors in resolving notification channels,
 * ensuring troubleshooting and debugging of issues related to missing or misconfigured channels.
 */
public class NotificationChannelWasNotFoundException extends RuntimeException {

    public NotificationChannelWasNotFoundException(String message) {
        super(message);
    }
}
