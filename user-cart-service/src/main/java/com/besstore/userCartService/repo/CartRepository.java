package com.besstore.userCartService.repo;

import com.besstore.userCartService.model.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Cart entities.
 * Provides methods for CRUD operations on carts and custom query methods.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Finds a cart by the user ID.
     *
     * @param userId the ID of the user whose cart to find
     * @return an Optional containing the cart if found, or empty if not found
     */
    Optional<Cart> findCartByUserId(@NonNull Long userId);

}
