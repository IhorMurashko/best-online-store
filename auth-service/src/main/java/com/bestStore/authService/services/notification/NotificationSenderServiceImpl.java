package com.bestStore.authService.services.notification;

import com.bestStore.Result;
import com.bestStore.authService.exceptions.utils.StatusRuntimeExceptionToRuntimeExceptionConvertor;
import com.bestStore.notification.Letter;
import com.bestStore.notification.NotificationServiceGrpc;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationSenderServiceImpl implements NotificationSenderService {

    @GrpcClient("notification-service")
    private NotificationServiceGrpc.NotificationServiceStub asyncService;

    @GrpcClient("notification-service")
    private NotificationServiceGrpc.NotificationServiceBlockingStub blockingService;

    @Override
    public void sendNotification(@NonNull Letter letter, boolean isAsync) {
        if (isAsync) {
            asyncService.send(letter, new StreamObserver<Result>() {
                @Override
                public void onNext(Result result) {
                    log.info("Notification sent, result: {}", result.getResult());
                }

                @Override
                public void onError(Throwable t) {
                    log.error("Failed to send notification", t);
                }

                @Override
                public void onCompleted() {
                    log.debug("Notification sending completed");
                }
            });
        } else {

            try {
                Result result = blockingService.withDeadlineAfter(5, TimeUnit.SECONDS)
                        .send(letter);
                log.info("Notification sent, result: {}", result.getResult());
            } catch (StatusRuntimeException ex) {
                log.error("Failed to send notification", ex);
                throw StatusRuntimeExceptionToRuntimeExceptionConvertor.convert(ex);
            }
        }
    }
}
