package com.besstore.userCartService.service.cartService;

import com.besstore.userCartService.mapper.ItemMapper;
import com.besstore.userCartService.model.cart.Cart;
import com.besstore.userCartService.model.item.Item;
import com.besstore.userCartService.repo.CartRepository;
import com.common.lib.cartModule.itemDto.ItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private CartServiceImpl cartService;

    private Long userId;
    private Cart existingCart;
    private Set<ItemDto> itemDtos;
    private Set<Item> items;

    @BeforeEach
    void setUp() {
        userId = 1L;
        existingCart = new Cart();
        existingCart.setUserId(userId);
        existingCart.setItems(new HashSet<>());

        itemDtos = new HashSet<>();
        ItemDto itemDto = new ItemDto(101L, 2, "19.99");
        itemDtos.add(itemDto);

        items = new HashSet<>();
        Item item = new Item();
        item.setProductId(101L);
        item.setQuantity(2);
        item.setPriceSnapshot(BigDecimal.valueOf(19.99));
        items.add(item);
    }

    @Test
    void addItemToTheCart_ExistingCart_ShouldAddItemsToCart() {
        // Arrange
        when(cartRepository.findCartByUserId(userId)).thenReturn(Optional.of(existingCart));
        when(itemMapper.toEntitySet(itemDtos)).thenReturn(items);
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Cart result = cartService.addItemToTheCart(userId, itemDtos);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        verify(cartRepository).findCartByUserId(userId);
        verify(itemMapper).toEntitySet(itemDtos);
        verify(cartRepository).save(existingCart);

        // Verify that each item has the cart reference set
        for (Item item : result.getItems()) {
            assertEquals(result, item.getCart());
        }
    }

    @Test
    void addItemToTheCart_NewCart_ShouldCreateCartAndAddItems() {
        // Arrange
        when(cartRepository.findCartByUserId(userId)).thenReturn(Optional.empty());
        when(itemMapper.toEntitySet(itemDtos)).thenReturn(items);
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Cart result = cartService.addItemToTheCart(userId, itemDtos);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(1, result.getItems().size());
        verify(cartRepository).findCartByUserId(userId);
        verify(itemMapper).toEntitySet(itemDtos);
        verify(cartRepository).save(any(Cart.class));

        // Verify that each item has the cart reference set
        for (Item item : result.getItems()) {
            assertEquals(result, item.getCart());
        }
    }
}
