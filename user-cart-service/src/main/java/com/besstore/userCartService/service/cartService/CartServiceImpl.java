package com.besstore.userCartService.service.cartService;

import com.besstore.userCartService.mapper.ItemMapper;
import com.besstore.userCartService.model.cart.Cart;
import com.besstore.userCartService.model.item.Item;
import com.besstore.userCartService.repo.CartRepository;
import com.besstore.userCartService.utils.FieldAdapter;
import com.common.lib.cartModule.itemDto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ItemMapper itemMapper;

    @Override
    public Cart save(@NonNull Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Cart addItemToTheCart(@NonNull Long userId, @NonNull Set<ItemDto> itemDtos) {

        Optional<Cart> optionalUserCart = findCartByUserId(userId);
        Cart cart;

        if (optionalUserCart.isPresent()) {
            cart = optionalUserCart.get();
        } else {
            cart = new Cart();
            cart.setUserId(userId);
        }


        for (ItemDto dto : itemDtos) {
            Item item = new Item(
                    dto.productId(),
                    dto.quantity(),
                    FieldAdapter.toPrice(
                            dto.priceSnapshot()
                    ), cart

            );
            cart.getItems().add(item);
        }

        return cartRepository.save(cart);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Cart removeItemFromTheCart(@NonNull Long userId, @NonNull ItemDto itemDto) {
        return null;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Cart clearTheCart(@NonNull Long userId) {
        return null;
    }

    @Override
    public Optional<Cart> getCartByCartId(@NonNull Long cartId) {
        return cartRepository.findById(cartId);
    }

    @Override
    public Optional<Cart> findCartByUserId(@NonNull Long userId) {
        return cartRepository.findCartByUserId(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeCartByCartId(@NonNull Long cartId) {
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeCartByUserId(@NonNull Long userId) {
    }
}
