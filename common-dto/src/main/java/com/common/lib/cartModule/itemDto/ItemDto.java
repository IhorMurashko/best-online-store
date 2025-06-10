package com.common.lib.cartModule.itemDto;

import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

/**
 * Data Transfer Object representing an item in a shopping cart.
 *
 * @param productId     the unique identifier of the product; must not be null
 * @param imageUrl      the URL of the product image; must not be null
 * @param quantity      the quantity of the product; must be positive and not null
 * @param priceSnapshot the price information snapshot of the product; must not be null
 */
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
