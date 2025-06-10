package com.besstore.userCartService.service.cartService;

import com.besstore.userCartService.exception.UserCartNotFoundException;
import com.besstore.userCartService.mapper.ItemMapper;
import com.besstore.userCartService.model.cart.Cart;
import com.besstore.userCartService.model.item.Item;
import com.besstore.userCartService.repo.CartRepository;
import com.besstore.userCartService.utils.ExceptionMessageProvider;
import com.common.lib.cartModule.itemDto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of the CartService interface.
 * Provides functionality for managing user shopping carts, including adding, removing,
 * and retrieving cart items. Uses caching to improve performance for frequently accessed carts.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ItemMapper itemMapper;

    /**
     * Saves a cart to the database.
     *
     * @param cart the cart to save
     * @return the saved cart
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    protected Cart save(@NonNull Cart cart) {
        return cartRepository.save(cart);
    }

    /**
     * {@inheritDoc}
     * <p>
     * If the cart doesn't exist for the user, a new one is created.
     * If the item already exists in the cart, its quantity is updated.
     * Otherwise, a new item is added to the cart.
     * The cart cache is evicted to ensure fresh data on subsequent requests.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = "cart", key = "#userId")
    public Set<ItemDto> addItemToTheCart(@NonNull Long userId, @NonNull ItemDto itemDto) {

        Cart cart = findCartByUserId(userId)
                .orElseGet(() -> new Cart(userId));


        Optional<Item> existingItem = cart.getItems().stream()
                .filter(item -> Objects.equals(item.getProductId(), itemDto.productId()))
                .findFirst();


        if (existingItem.isPresent()) {

            itemMapper.updateEntityFromDto(itemDto, existingItem.get());

        } else {

            Item item = itemMapper.toEntity(itemDto);
            item.setCart(cart);

            cart.getItems().add(item);
        }

        log.info("added item: {}  to the cart: {}", itemDto.productId(), userId);

        save(cart);

        return itemMapper.toDtoSet(cart.getItems());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Removes the item with the specified product ID from the user's cart.
     * The cart cache is evicted to ensure fresh data on subsequent requests.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    @CacheEvict(value = "cart", key = "#userId")
    public Set<ItemDto> removeItemFromTheCart(@NonNull Long userId, @NonNull Long productId) {

        Cart cart = getCart(userId);

        cart.getItems().stream()
                .filter(item -> Objects.equals(item.getProductId(), productId))
                .findFirst()
                .ifPresent(item -> cart.getItems().remove(item));

        log.info("removed item: {}  from the cart: {}", productId, userId);

        save(cart);


        return itemMapper.toDtoSet(cart.getItems());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Removes all items from the user's cart.
     * The cart cache is evicted to ensure fresh data on subsequent requests.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = "cart", key = "#userId")
    public void clearTheCart(@NonNull Long userId) {
        Cart cart = getCart(userId);
        cart.getItems().clear();
        log.info("cleared the cart for the user: {}", userId);
        save(cart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Cart> findCartByCartId(@NonNull Long cartId) {
        return cartRepository.findById(cartId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Cart> findCartByUserId(@NonNull Long userId) {
        return cartRepository.findCartByUserId(userId);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Results are cached to improve performance for frequently accessed carts.
     * If the cart is not found, an empty set is returned.
     */
    @Override
    @Cacheable(value = "cart", key = "#userId")
    public Set<ItemDto> getCartItemsByUserId(@NonNull Long userId) {
        try {
            Cart cart = getCart(userId);
            return itemMapper.toDtoSet(cart.getItems());
        } catch (UserCartNotFoundException ex) {
            log.warn(ex.getMessage());
            return Collections.emptySet();
        }
    }

    /**
     * Helper method to get a cart by user ID.
     * Throws UserCartNotFoundException if the cart is not found.
     *
     * @param userId the ID of the user whose cart to retrieve
     * @return the user's cart
     * @throws UserCartNotFoundException if the cart is not found
     */
    private Cart getCart(Long userId) {
        Optional<Cart> cartByUserId = cartRepository.findCartByUserId(userId);
        if (cartByUserId.isPresent()) {
            return cartByUserId.get();
        } else {
            log.warn("User cart not found for user id: {}. Throwing UserCartNotFoundException.", userId);
            throw new UserCartNotFoundException(
                    String.format(
                            ExceptionMessageProvider.USER_CART_NOT_FOUND, userId
                    )
            );
        }
    }
}
