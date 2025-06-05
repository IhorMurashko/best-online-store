package com.besstore.userCartService.service.cartService;

import com.besstore.userCartService.model.cart.Cart;
import com.common.lib.cartModule.itemDto.ItemDto;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.Set;

public interface CartService {

    Cart save(@NonNull Cart cart);

    Cart addItemToTheCart(@NonNull Long userId, @NonNull Set<ItemDto> itemDtos);

    Cart removeItemFromTheCart(@NonNull Long userId, @NonNull ItemDto itemDto);

    Cart clearTheCart(@NonNull Long userId);

    Optional<Cart> getCartByCartId(@NonNull Long cartId);

    Optional<Cart> findCartByUserId(@NonNull Long userId);

    void removeCartByCartId(@NonNull Long cartId);

    void removeCartByUserId(@NonNull Long userId);
}
