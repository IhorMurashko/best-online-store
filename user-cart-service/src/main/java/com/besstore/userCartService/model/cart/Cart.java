package com.besstore.userCartService.model.cart;

import com.besstore.userCartService.model.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a user's shopping cart.
 * A cart belongs to a specific user and contains a collection of items.
 */
@Entity
@Table(name = "carts")
@NoArgsConstructor
@Getter
@Setter
public class Cart {

    /**
     * Unique identifier for the cart.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq_generator")
    @SequenceGenerator(name = "cart_seq_generator", sequenceName = "cart_seq", allocationSize = 1)
    protected Long id;

    /**
     * The ID of the user who owns this cart.
     */
    @Column(nullable = false)
    protected Long userId;

    /**
     * Collection of items in the cart.
     * Items are removed from the database when removed from this collection (orphanRemoval=true).
     */
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> items = new HashSet<>();

    /**
     * Creates a new cart for the specified user.
     *
     * @param userId the ID of the user who will own this cart
     */
    public Cart(Long userId) {
        this.userId = userId;
    }
}
