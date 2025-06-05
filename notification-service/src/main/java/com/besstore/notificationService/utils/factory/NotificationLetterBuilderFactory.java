package com.besstore.notificationService.utils.factory;

import com.besstore.notificationService.exception.NotificationBuilderWasNotFoundException;
import com.besstore.notificationService.utils.contentBuilder.NotificationContentBuilder;
import com.common.lib.notificationModule.request.constants.NotificationChannel;
import com.common.lib.notificationModule.request.constants.NotificationType;

/**
 * Factory interface for retrieving {@link NotificationContentBuilder} implementations.
 *
 * <p>Provides a method to resolve the appropriate builder based on the
 * notification type and delivery channel.
 *
 * <p>Intended to streamline the process of selecting the correct
 * {@link NotificationContentBuilder} implementation for constructing
 * notification content.
 *
 * <p>Throws an exception if no suitable builder is found for the specified
 * type and channel.
 *
 * @see NotificationContentBuilder
 * @see NotificationType
 * @see NotificationChannel
 * @see NotificationBuilderWasNotFoundException
 *
 * @author Ihor Murashko
 */
@FunctionalInterface
public interface NotificationLetterBuilderFactory {

    NotificationContentBuilder getNotificationBuilder(NotificationType type, NotificationChannel channel);

}
