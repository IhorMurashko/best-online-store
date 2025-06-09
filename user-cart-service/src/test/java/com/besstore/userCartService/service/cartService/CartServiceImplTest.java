package com.besstore.userCartService.service.cartService;

import com.besstore.userCartService.mapper.ItemMapper;
import com.besstore.userCartService.model.cart.Cart;
import com.besstore.userCartService.model.item.Item;
import com.besstore.userCartService.repo.CartRepository;
import com.common.lib.cartModule.itemDto.ItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private CartServiceImpl cartService;


    @Captor
    ArgumentCaptor<Long> userIdArgumentCaptor;
    @Captor
    ArgumentCaptor<Cart> cartArgumentCaptor;
    @Captor
    ArgumentCaptor<Item> itemArgumentCaptor;
    @Captor
    ArgumentCaptor<ItemDto> itemDtoArgumentCaptor;
    @Captor
    ArgumentCaptor<Set<Item>> itemSetArgumentCaptor;


    private long userId;
    private long productId;
    private short quantity;
    private BigDecimal priceSnapshot;
    private Item item;
    private ItemDto itemDto;

    @BeforeEach
    void setUp() {
        userId = 1L;
        productId = 1L;
        quantity = 4;
        priceSnapshot = new BigDecimal("10.00");

        item = new Item(productId, quantity, priceSnapshot);

        itemDto = new ItemDto(productId, quantity, String.valueOf(priceSnapshot));
    }


    @Test
    @DisplayName("success add first item")
    void should_AddFirstItem_When_CartWasntCreated() {
        //given

        doReturn(Optional.empty()).when(cartRepository).findCartByUserId(userId);
        doReturn(item).when(itemMapper).toEntity(any(ItemDto.class));

        doAnswer(invocation -> invocation.getArgument(0)).when(cartRepository).save(any(Cart.class));

        Set<ItemDto> itemDtoSet = Set.of(itemDto);
        doReturn(itemDtoSet).when(itemMapper).toDtoSet(anySet());


        //when
        Set<ItemDto> resultSet = cartService.addItemToTheCart(userId, itemDto);


        //then
        assertNotNull(resultSet);
        assertEquals(1, resultSet.size());
        assertTrue(resultSet.contains(itemDto));


        //verify
        verify(cartRepository, times(1)).findCartByUserId(userId);
        verify(itemMapper, times(0)).updateEntityFromDto(any(ItemDto.class), any(Item.class));
        verify(itemMapper, times(1)).toEntity(any(ItemDto.class));
        verify(cartRepository, times(1)).save(any(Cart.class));
        verify(itemMapper, times(1)).toDtoSet(anySet());

        verify(cartRepository).findCartByUserId(userIdArgumentCaptor.capture());
        assertEquals(userId, userIdArgumentCaptor.getValue().longValue());
        verify(itemMapper).toEntity(itemDtoArgumentCaptor.capture());
        assertEquals(itemDto, itemDtoArgumentCaptor.getValue());
        verify(cartRepository).save(cartArgumentCaptor.capture());
        assertEquals(item, cartArgumentCaptor.getValue().getItems().iterator().next());
        verify(itemMapper).toDtoSet(itemSetArgumentCaptor.capture());
        assertEquals(item, itemSetArgumentCaptor.getValue().iterator().next());

        verifyNoMoreInteractions(cartRepository, itemMapper);
    }






}