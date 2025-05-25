package com.bestStore.userService.services.userCrudService;

import com.bestStore.userService.model.User;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserCrudService {

    Optional<User> findByEmail(@NonNull String email);

    Optional<User> findById(@NonNull Long id);

    User save(@NonNull User user);

    void deleteById(@NonNull long id);
    void deleteByEmail(@NonNull String email);

    boolean isEmailExist(@NonNull String email);

    boolean isUserExistById(@NonNull long id);


}
