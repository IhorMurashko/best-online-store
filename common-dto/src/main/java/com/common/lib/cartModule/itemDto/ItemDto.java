package com.common.lib.cartModule.itemDto;

import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

public record ItemDto(
        @NonNull
        Long productId,
        @NonNull
        String imageUrl,
        @NonNull
        @Positive
        Short quantity,
        @NonNull
        String priceSnapshot
) {
}
