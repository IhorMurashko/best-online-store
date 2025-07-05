package com.besstore.grpcService;

import com.besstore.notificationService.service.DispatcherService;
import com.bestStore.Result;
import com.bestStore.notification.Letter;
import com.bestStore.notification.NotificationServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class NotificationGrpcService extends NotificationServiceGrpc.NotificationServiceImplBase {

    private final DispatcherService dispatcherService;

    @Override
    public void send(Letter request, StreamObserver<Result> responseObserver) {

    }
}
