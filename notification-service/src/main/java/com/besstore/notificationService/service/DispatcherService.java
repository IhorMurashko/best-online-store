package com.besstore.notificationService.service;

import com.common.lib.notificationModule.request.notification.BaseNotification;
import org.springframework.lang.NonNull;
/**
 * Service responsible for coordinating notification delivery.
 *
 * <p>Resolves appropriate content builders and sender implementations
 * based on the provided notification channel.
 *
 * @author Ihor Murashko
 */
@FunctionalInterface
public interface DispatcherService {

    void send(@NonNull BaseNotification notification);

}
