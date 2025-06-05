package com.besstore.userCartService.repo;

import com.besstore.userCartService.model.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    Optional<Cart> findCartByUserId(@NonNull Long userId);


}
