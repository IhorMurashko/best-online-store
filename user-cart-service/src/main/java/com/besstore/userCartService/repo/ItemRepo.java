package com.besstore.userCartService.repo;

import com.besstore.userCartService.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Repository interface for Item entities.
 * Provides methods for CRUD operations on items and custom query methods.
 */
@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {

    /**
     * Retrieves all items in a specific cart.
     *
     * @param cartId the ID of the cart whose items to retrieve
     * @return a set of items in the specified cart
     */
    Set<Item> getItemsByCartId(Long cartId);

}
