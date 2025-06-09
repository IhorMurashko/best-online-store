package com.besstore.userCartService.repo;

import com.besstore.userCartService.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {


    Set<Item> getItemsByCartId(Long cartId);

}
