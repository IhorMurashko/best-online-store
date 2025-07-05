package com.bestStore.authService.utils;

import com.bestStore.notification.Letter;
import com.bestStore.notification.NotificationChannel;
import com.bestStore.notification.NotificationType;
import com.google.protobuf.NullValue;
import com.google.protobuf.Value;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Map;

@UtilityClass
@Slf4j
public class NotificationLetterBuilder {

    public Letter builder(@NonNull NotificationType type,
                          @NonNull NotificationChannel channel,
                          @NonNull String address,
                          @Nullable String subject,
                          @Nullable Map<String, Object> payload
    ) {

        if (subject == null) {
            log.debug("letter subject is null");
            subject = "no-reply notification";
        }

        Letter.Builder builder = Letter.newBuilder()
                .setType(type)
                .setChannel(channel)
                .setAddress(address)
                .setSubject(subject);
        log.debug("letter was built");

        if (payload != null) {
            payload.forEach((key, value) -> {
                builder.putData(key, convertToValue(value));
            });
            log.debug("payload was built");
        }

        return builder.build();
    }

    private Value convertToValue(Object obj) {
        Value.Builder valueBuilder = Value.newBuilder();

        if (obj == null) {
            return valueBuilder.setNullValue(NullValue.NULL_VALUE).build();
        } else if (obj instanceof String s) {
            return valueBuilder.setStringValue(s).build();
        } else if (obj instanceof Boolean b) {
            return valueBuilder.setBoolValue(b).build();
        } else if (obj instanceof Integer i) {
            return valueBuilder.setNumberValue(i).build();
        } else if (obj instanceof Long l) {
            return valueBuilder.setNumberValue(l).build();
        } else if (obj instanceof Double d) {
            return valueBuilder.setNumberValue(d).build();
        } else {
            // fallback: serialize to string (or throw)
            return valueBuilder.setStringValue(obj.toString()).build();
        }
    }
}

