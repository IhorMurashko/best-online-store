package com.bestStore.gateway.exception;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class WebfluxExceptionResponseStatusBuilder {

    private WebfluxExceptionResponseStatusBuilder(){

    }

    public static Mono<Void> buildWebFluxExceptionResponse(ServerWebExchange exchange,
                                                           HttpStatus httpStatus,
                                                           String message,
                                                           String contentType) {

        DataBuffer buffer = exchange.getResponse()
                .bufferFactory()
                .wrap(message.getBytes(StandardCharsets.UTF_8));

        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().add("Content-Type", contentType);

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}