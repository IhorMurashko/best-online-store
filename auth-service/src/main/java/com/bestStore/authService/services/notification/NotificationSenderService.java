package com.bestStore.authService.services.notification;

import com.bestStore.notification.Letter;
import org.springframework.lang.NonNull;

public interface NotificationSenderService {
    void sendNotification(@NonNull Letter letter, boolean isAsync);
}
