package com.besstore.userCartService.model.cart;

import com.besstore.userCartService.model.item.Item;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@Getter
@Setter
public class Cart extends AbstractBaseCartModel {


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> items = new HashSet<>();


    public Cart(Long userId, Set<Item> items) {
        super(userId);
        this.items = items;
    }
}
