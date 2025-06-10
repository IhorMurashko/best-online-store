package com.besstore.userCartService.utils;

import lombok.experimental.UtilityClass;

/**
 * Utility class that provides message templates for exceptions.
 * Contains constants for standardized error messages used throughout the application.
 */
@UtilityClass
public class ExceptionMessageProvider {

    /**
     * Message template for when a user's cart is not found.
     * The placeholder %d will be replaced with the user ID.
     */
    public final String USER_CART_NOT_FOUND = "User cart with user ID: %d not found";

}
