package com.common.lib.cartModule.itemDto;

import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

public record ItemDto(
        @NonNull
        Long productId,
        @NonNull
        @Positive
        Integer quantity,
        @NonNull
        String priceSnapshot
) {
}
