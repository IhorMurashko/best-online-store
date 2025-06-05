package com.besstore.userCartService.model.item;

import com.besstore.userCartService.model.cart.Cart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item extends AbstractBaseItemModel {


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public Item(Long productId, int quantity, BigDecimal priceSnapshot, Cart cart) {
        super(productId, quantity, priceSnapshot);
        this.cart = cart;
    }
}