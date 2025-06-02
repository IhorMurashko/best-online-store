package com.besstore.notificationService.sender.factory;

import com.besstore.notificationService.sender.NotificationSender;
import com.common.lib.notificationModule.request.constants.NotificationChannel;
import org.springframework.lang.NonNull;

/**
 * Functional interface responsible for providing appropriate {@link NotificationSender}
 * based on the given {@link NotificationChannel}.
 *
 * <p>Implementations of this interface are expected to resolve the correct sender
 * for handling notifications over the specified channel.
 *
 * <p>If no matching sender is found for the channel, an appropriate exception should be thrown.
 *
 * <p>Used in the notification dispatching process to delegate notification delivery
 * to the correct channel-specific sender.
 *
 * @author Ihor Murashko
 */
@FunctionalInterface
public interface SenderFactory {

    NotificationSender getSender(@NonNull NotificationChannel channel);
}
