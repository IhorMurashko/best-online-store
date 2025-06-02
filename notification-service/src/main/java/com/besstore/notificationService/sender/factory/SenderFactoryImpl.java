package com.besstore.notificationService.sender.factory;

import com.besstore.notificationService.exception.ExceptionMessages;
import com.besstore.notificationService.exception.NotificationChannelWasNotFoundException;
import com.besstore.notificationService.sender.NotificationSender;
import com.common.lib.notificationModule.request.constants.NotificationChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * Concrete implementation of {@link SenderFactory} for resolving {@link NotificationSender}.
 *
 * <p>This class uses the {@link ApplicationContext} to find all available beans of type
 * {@link NotificationSender} and selects the one that matches the provided {@link NotificationChannel}.
 * If no matching sender is found, a {@link NotificationChannelWasNotFoundException} is thrown.
 *
 * <p>It facilitates dynamic resolution and dependency injection of the required notification sender
 * based on the notification channel.
 *
 * <p>Primarily used in scenarios where different notification channels need specialized handling
 * via their respective sender implementations.
 *
 * @author Ihor Murashko
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SenderFactoryImpl implements SenderFactory {

    private final ApplicationContext context;


    /**
     * Resolves the appropriate {@link NotificationSender} for the provided {@link NotificationChannel}.
     *
     * @param channel The notification channel for which the sender is required.
     * @return The resolved {@link NotificationSender} instance.
     */
    @Override
    public NotificationSender getSender(@NonNull NotificationChannel channel) {
        Supplier<NotificationSender> supplier = () -> context.getBeansOfType(NotificationSender.class)
                .values().stream()
                .filter(sender -> sender.getNotificationChannel().equals(channel))
                .findFirst()
                .orElseThrow(
                        () -> new NotificationChannelWasNotFoundException(String.format(
                                ExceptionMessages.NOTIFICATION_CHANNEL_WAS_NOT_FOUND, channel
                        )
                        ));

        return supplier.get();
    }
}