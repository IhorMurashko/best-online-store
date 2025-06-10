package com.besstore.userCartService.service.cartService;

import com.besstore.userCartService.model.cart.Cart;
import com.common.lib.cartModule.itemDto.ItemDto;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.Set;

/**
 * Service interface for managing user shopping carts.
 * Provides methods for adding, removing, and retrieving cart items.
 */
public interface CartService {

    /**
     * Adds an item to the user's cart or updates its quantity if it already exists.
     *
     * @param userId the ID of the user whose cart is being modified
     * @param itemDto the item to add to the cart
     * @return the updated set of items in the cart
     */
    Set<ItemDto> addItemToTheCart(@NonNull Long userId, @NonNull ItemDto itemDto);

    /**
     * Removes an item from the user's cart based on the product ID.
     *
     * @param userId the ID of the user whose cart is being modified
     * @param productId the ID of the product to remove from the cart
     * @return the updated set of items in the cart
     */
    Set<ItemDto> removeItemFromTheCart(@NonNull Long userId, @NonNull Long productId);

    /**
     * Removes all items from the user's cart.
     *
     * @param userId the ID of the user whose cart is being cleared
     */
    void clearTheCart(@NonNull Long userId);

    /**
     * Finds a cart by its ID.
     *
     * @param cartId the ID of the cart to find
     * @return an Optional containing the cart if found, or empty if not found
     */
    Optional<Cart> findCartByCartId(@NonNull Long cartId);

    /**
     * Finds a cart by the user ID.
     *
     * @param userId the ID of the user whose cart to find
     * @return an Optional containing the cart if found, or empty if not found
     */
    Optional<Cart> findCartByUserId(@NonNull Long userId);

    /**
     * Retrieves all items in the user's cart.
     *
     * @param userId the ID of the user whose cart items to retrieve
     * @return a set of items in the user's cart
     */
    Set<ItemDto> getCartItemsByUserId(@NonNull Long userId);

}
