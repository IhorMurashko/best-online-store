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
    private short existingQuantity;
    private short newQuantity;
    private BigDecimal existingPriceSnapshot;
    private BigDecimal newPriceSnapshot;
    private Item itemWithNewQuantity;
    private Item existingItem;
    private ItemDto newItemDto;
    private ItemDto existingItemDto;
    private Cart cart;
    private String imageUrl;

    @BeforeEach
    void setUp() {
        this.userId = 1L;
        this.productId = 1L;
        this.existingQuantity = 4;
        this.newQuantity = 40;
        this.existingPriceSnapshot = new BigDecimal("10.00");
        this.newPriceSnapshot = new BigDecimal("20.00");

        this.existingItem = new Item(productId, existingQuantity, existingPriceSnapshot);
        this.itemWithNewQuantity = new Item(productId, newQuantity, existingPriceSnapshot);

        this.newItemDto = new ItemDto(productId, imageUrl, newQuantity, String.valueOf(existingPriceSnapshot));
        this.existingItemDto = new ItemDto(productId, imageUrl, existingQuantity, String.valueOf(existingPriceSnapshot));

        this.cart = new Cart(userId);
        this.imageUrl = String.valueOf("image-url");
    }


    @Test
    @DisplayName("success add first item")
    void should_AddFirstItem_When_CartWasntCreated() {
        //given

        doReturn(Optional.empty()).when(cartRepository).findCartByUserId(userId);
        doReturn(itemWithNewQuantity).when(itemMapper).toEntity(any(ItemDto.class));

        doAnswer(invocation -> invocation.getArgument(0)).when(cartRepository).save(any(Cart.class));

        Set<ItemDto> itemDtoSet = Set.of(newItemDto);
        doReturn(itemDtoSet).when(itemMapper).toDtoSet(anySet());


        //when
        Set<ItemDto> resultSet = cartService.addItemToTheCart(userId, newItemDto);


        //then
        assertNotNull(resultSet);
        assertEquals(1, resultSet.size());
        assertTrue(resultSet.contains(newItemDto));


        //verify
        verify(cartRepository, times(1)).findCartByUserId(userId);
        verify(itemMapper, times(0)).updateEntityFromDto(any(ItemDto.class), any(Item.class));
        verify(itemMapper, times(1)).toEntity(any(ItemDto.class));
        verify(cartRepository, times(1)).save(any(Cart.class));
        verify(itemMapper, times(1)).toDtoSet(anySet());

        verify(cartRepository).findCartByUserId(userIdArgumentCaptor.capture());
        assertEquals(userId, userIdArgumentCaptor.getValue().longValue());
        verify(itemMapper).toEntity(itemDtoArgumentCaptor.capture());
        assertEquals(newItemDto, itemDtoArgumentCaptor.getValue());
        verify(cartRepository).save(cartArgumentCaptor.capture());
        assertEquals(itemWithNewQuantity, cartArgumentCaptor.getValue().getItems().iterator().next());
        verify(itemMapper).toDtoSet(itemSetArgumentCaptor.capture());
        assertEquals(itemWithNewQuantity, itemSetArgumentCaptor.getValue().iterator().next());

        verifyNoMoreInteractions(cartRepository, itemMapper);
    }


    @Test
    @DisplayName("update existing quantity")
    void should_UpdateExistingQuantity_When_ItemAlreadyExistedInACard() {

        // given
        doReturn(Optional.of(cart)).when(cartRepository).findCartByUserId(userId);
        cart.getItems().add(existingItem);

        doNothing().when(itemMapper).updateEntityFromDto(any(ItemDto.class), any(Item.class));
        doAnswer(invocation -> invocation.getArgument(0)).when(cartRepository).save(any(Cart.class));
        Set<ItemDto> itemDtoSet = Set.of(newItemDto);
        doReturn(itemDtoSet).when(itemMapper).toDtoSet(anySet());

        Set<ItemDto> resultSet = cartService.addItemToTheCart(userId, newItemDto);
        assertNotNull(resultSet);
        assertEquals(1, resultSet.size());
        assertTrue(resultSet.contains(newItemDto));
        assertEquals(newQuantity, resultSet.iterator().next().quantity());



        verify(cartRepository, times(1)).findCartByUserId(userId);
        verify(itemMapper, times(1)).updateEntityFromDto(any(ItemDto.class), any(Item.class));
        verify(itemMapper, times(0)).toEntity(any(ItemDto.class));
        verify(cartRepository, times(1)).save(any(Cart.class));
        verify(itemMapper, times(1)).toDtoSet(anySet());

        verify(cartRepository).findCartByUserId(userIdArgumentCaptor.capture());
        assertEquals(userId, userIdArgumentCaptor.getValue().longValue());
        verify(itemMapper).updateEntityFromDto(itemDtoArgumentCaptor.capture(), itemArgumentCaptor.capture());
        assertEquals(newItemDto, itemDtoArgumentCaptor.getValue());
        assertEquals(existingItem, itemArgumentCaptor.getValue());
        verify(cartRepository).save(cartArgumentCaptor.capture());
        assertEquals(itemWithNewQuantity, cartArgumentCaptor.getValue().getItems().iterator().next());
        verify(itemMapper).toDtoSet(itemSetArgumentCaptor.capture());
        assertEquals(itemWithNewQuantity, itemSetArgumentCaptor.getValue().iterator().next());
        verifyNoMoreInteractions(cartRepository, itemMapper);

    }
}