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

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ItemMapper itemMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Cart save(@NonNull Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    @CacheEvict(value = "cart", key = "#userId")
    public Set<ItemDto> removeItemFromTheCart(@NonNull Long userId, @NonNull Long productId) {

        Cart cart = getCart(userId);

        cart.getItems()
                .removeIf(item -> Objects.equals(item.getProductId(), productId));

        log.info("removed item: {}  from the cart: {}", productId, userId);

        save(cart);


        return itemMapper.toDtoSet(cart.getItems());
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = "cart", key = "#userId")
    public void clearTheCart(@NonNull Long userId) {
        Cart cart = getCart(userId);
        cart.getItems().clear();
        log.info("cleared the cart for the user: {}", userId);
        save(cart);
    }

    @Override
    public Optional<Cart> findCartByCartId(@NonNull Long cartId) {
        return cartRepository.findById(cartId);
    }

    @Override
    public Optional<Cart> findCartByUserId(@NonNull Long userId) {
        return cartRepository.findCartByUserId(userId);
    }

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
