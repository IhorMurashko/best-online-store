package com.besstore.notificationService.exception;

import lombok.experimental.UtilityClass;

/**
 * Utility class that provides predefined exception message templates for various error scenarios
 * related to notifications. These messages can be used to format exception messages when certain
 * conditions (like missing notification channels or builders) arise within the notification service.
 * <br>
 * The provided constants serve as templates, using placeholders for dynamic values. They are intended
 * to be used with {@link String#format(String, Object...)} to produce detailed error messages.
 *
 * Example Usage:
 * {@code
 *   String formattedMessage = String.format(NOTIFICATION_CHANNEL_WAS_NOT_FOUND, channel);
 *   throw new NotificationChannelWasNotFoundException(formattedMessage);
 * }
 *
 * Exception message templates:
 * NOTIFICATION_CHANNEL_WAS_NOT_FOUND - Used when a specific notification channel is not found.
 * NOTIFICATION_BUILDER_WAS_NOT_FOUND - Used when a matching notification builder is unavailable.
 *
 * @author Ihor Murashko
 */
@UtilityClass
public class ExceptionMessages {

    public static final String NOTIFICATION_CHANNEL_WAS_NOT_FOUND = "Notification type %s was not found";
    public static final String NOTIFICATION_BUILDER_WAS_NOT_FOUND = "Notification builder with type: %s and channel: %s was not found";

}
