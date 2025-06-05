package com.besstore.notificationService.sender;

import com.common.lib.notificationModule.request.constants.NotificationChannel;
import com.common.lib.notificationModule.request.notification.BaseNotification;
import org.springframework.lang.NonNull;

/**
 * Interface for sending notifications across different channels.
 *
 * This interface defines methods for retrieving the notification channel
 * and sending notification messages. Implementations of this interface
 * are responsible for handling specific channels (e.g., EMAIL, SMS, PUSH)
 * and ensuring delivery of the notification's content to the intended recipient.
 *
 * @author Ihor Murashko
 */
public interface NotificationSender {

    NotificationChannel getNotificationChannel();

    void send(@NonNull BaseNotification notification, @NonNull String content);

}
