package com.bestStore.userService.repositories;

import com.bestStore.userService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(@NonNull String email);

    Optional<User> findById(@NonNull Long id);

    void deleteById(@NonNull Long id);
    void deleteByEmail(@NonNull String email);

    boolean existsByEmail(@NonNull String email);
    boolean existsById(@NonNull Long id);


}
