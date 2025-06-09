package com.besstore.userCartService.model.item;

import com.besstore.userCartService.model.cart.Cart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Entity class representing an item in a user's shopping cart.
 * Each item corresponds to a product with a specific quantity and price.
 */
@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item {
    /**
     * Unique identifier for the item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq_generator")
    @SequenceGenerator(name = "item_seq_generator", sequenceName = "item_seq", allocationSize = 1)
    protected Long id;

    /**
     * The ID of the product this item represents.
     */
    @Column(nullable = false)
    protected Long productId;

    /**
     * The quantity of the product in the cart.
     */
    @Column(nullable = false)
    protected short quantity;

    /**
     * The price of the product at the time it was added to the cart.
     * This allows for price tracking even if the product's price changes later.
     */
    @Column(nullable = false)
    protected BigDecimal priceSnapshot;
    /**
     * URL of the image associated with the item.
     * This field is required and cannot be null.
     */
    @Column(nullable = false)
    protected String imageUrl;

    /**
     * The cart that contains this item.
     * This field is excluded from JSON serialization.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    /**
     * Creates a new item with the specified product details.
     *
     * @param productId     the ID of the product
     * @param quantity      the quantity of the product
     * @param priceSnapshot the price of the product at the time of adding to cart
     */
    public Item(Long productId, short quantity, BigDecimal priceSnapshot) {
        this.productId = productId;
        this.quantity = quantity;
        this.priceSnapshot = priceSnapshot;
    }

    /**
     * Compares this item with another object for equality.
     * Items are considered equal if they have the same product ID.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(productId, item.productId);
    }

    /**
     * Generates a hash code for this item based on its product ID.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(productId);
    }
}
