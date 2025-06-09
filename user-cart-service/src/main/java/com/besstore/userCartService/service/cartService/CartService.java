package com.besstore.userCartService.service.cartService;

import com.besstore.userCartService.model.cart.Cart;
import com.common.lib.cartModule.itemDto.ItemDto;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.Set;

public interface CartService {

    Cart save(@NonNull Cart cart);

    Set<ItemDto> addItemToTheCart(@NonNull Long userId, @NonNull ItemDto itemDto);

    Set<ItemDto> removeItemFromTheCart(@NonNull Long userId, @NonNull Long itemId);

    void clearTheCart(@NonNull Long userId);

    Optional<Cart> findCartByCartId(@NonNull Long cartId);
    Optional<Cart> findCartByUserId(@NonNull Long userId);


    Set<ItemDto> getCartItemsByUserId(@NonNull Long userId);

}
