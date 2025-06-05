package com.besstore.userCartService.controller;

import com.besstore.userCartService.mapper.ItemMapper;
import com.besstore.userCartService.model.cart.Cart;
import com.besstore.userCartService.model.item.Item;
import com.besstore.userCartService.service.cartService.CartService;
import com.common.lib.cartModule.itemDto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/us")
@RequiredArgsConstructor
public class TestController {

    private final ItemMapper itemMapper;
    private final CartService cartService;

    @PostMapping("/test")
    public ResponseEntity<Cart> test(
            @RequestHeader("userId") Long userId,
            @RequestBody Set<ItemDto> itemDto) {

        return ResponseEntity.ok(cartService.addItemToTheCart(userId, itemDto));
    }

    @PostMapping("/test2")
    public ResponseEntity<Set<Item>> test2(
            @RequestHeader("userId") Long userId,
            @RequestBody Set<ItemDto> itemDto) {
        Cart cart = cartService.addItemToTheCart(userId, itemDto);
        return ResponseEntity.ok(cart.getItems());

    }


}
