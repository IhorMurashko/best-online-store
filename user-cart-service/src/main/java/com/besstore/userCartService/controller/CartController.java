package com.besstore.userCartService.controller;

import com.besstore.userCartService.service.cartService.CartService;
import com.common.lib.cartModule.itemDto.ItemDto;
import com.common.lib.headers.HeadersProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * REST controller for managing user shopping carts.
 * Provides endpoints for adding, removing, and retrieving cart items.
 */

@Tag(name = "user cart controller",
        description = "CRUD operations for the carts.")

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Validated
public class CartController {


    private final CartService cartService;

    @Operation(summary = "Get user's cart items",
            description = "Retrieves all items in the user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cart items",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ItemDto.class)))),
            @ApiResponse(responseCode = "404", description = "User's cart not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User ID header missing")
    })
    @GetMapping("/get")
    ResponseEntity<Set<ItemDto>> getUserCart(
            @Parameter(description = "User ID from X-User-Id header", required = true)
            @RequestHeader(HeadersProvider.USER_ID_HEADER_NAME) @NonNull Long userId
    ) {
        return ResponseEntity.ok(cartService.getCartItemsByUserId(userId));
    }


    @Operation(summary = "Add item to cart",
            description = "Adds a new item to the user's cart or updates quantity if item already exists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully added to cart",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid item data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User ID header missing")
    })
    @PostMapping("/add")
    ResponseEntity<Set<ItemDto>> addToCart(
            @Parameter(description = "User ID from X-User-Id header", required = true)
            @RequestHeader(HeadersProvider.USER_ID_HEADER_NAME) @NonNull Long userId,
            @Parameter(description = "Item to add to cart", required = true)
            @RequestBody @NonNull @Valid ItemDto itemDto
    ) {
        return ResponseEntity.ok(cartService.addItemToTheCart(userId, itemDto));
    }

    @Operation(summary = "Remove item from cart",
            description = "Removes an item from the user's cart based on product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully removed from cart",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDto.class))),
            @ApiResponse(responseCode = "404", description = "Item not found in cart"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User ID header missing")
    })
    @DeleteMapping("/remove/{productId}")
    ResponseEntity<Set<ItemDto>> removeFromCart(
            @Parameter(description = "User ID from X-User-Id header", required = true)
            @RequestHeader(HeadersProvider.USER_ID_HEADER_NAME) @NonNull Long userId,
            @Parameter(description = "Product ID to remove from cart", required = true)
            @PathVariable("productId") @NonNull Long productId
    ) {
        return ResponseEntity.ok(cartService.removeItemFromTheCart(userId, productId));
    }

    @Operation(summary = "Clear user's cart", description = "Removes all items from the user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart successfully cleared"),
            @ApiResponse(responseCode = "404", description = "User's cart not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User ID header missing")
    })
    @DeleteMapping("/clear")
    ResponseEntity<HttpStatus> clearCart(
            @Parameter(description = "User ID from X-User-Id header", required = true)
            @RequestHeader(HeadersProvider.USER_ID_HEADER_NAME) @NonNull Long userId) {

        cartService.clearTheCart(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
