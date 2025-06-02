package com.common.lib.notificationModule.request.notification;

import com.common.lib.notificationModule.request.constants.NotificationChannel;
import com.common.lib.notificationModule.request.constants.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Map;


@Schema(
        name = "DefaultNotification",
        description = "Standard notification data structure used to send messages via different channels."
)
public record DefaultNotification(

        @Schema(
                description = "Type of the notification, defined by enum.",
                example = "REGISTRATION"
        )
        NotificationType type,

        @Schema(
                description = "Notification delivery channel.",
                example = "EMAIL"
        )
        NotificationChannel channel,

        @Schema(
                description = "Recipient identifier: email, phone number, device token, etc.",
                example = "user@example.com"
        )
        String to,

        @Schema(
                description = "Optional subject for the notification. Mainly used for email messages.",
                example = "Welcome to our service!"
        )
        @Nullable
        String subject,

        @Schema(
                description = "Optional map of dynamic data used to fill notification templates."
        )
        @Nullable
        Map<String, Object> data

) implements Serializable, BaseNotification {
}
