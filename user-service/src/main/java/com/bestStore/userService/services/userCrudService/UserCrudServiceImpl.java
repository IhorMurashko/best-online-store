package com.bestStore.userService.services.userCrudService;

import com.bestStore.userService.exceptions.ExceptionMessageProvider;
import com.bestStore.userService.exceptions.UserNotFoundException;
import com.bestStore.userService.model.User;
import com.bestStore.userService.repositories.UserRepository;
import com.bestStore.userService.utils.UserFieldAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCrudServiceImpl implements UserCrudService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(@NonNull String email) {

        log.info("Find user by email: {}", email);

        return userRepository.findByEmail(email.toLowerCase());
    }

    @Override
    public Optional<User> findById(@NonNull Long id) {
        log.info("Find user by id: {}", id);
        return userRepository.findById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED)
    @Override
    public User save(@NonNull User user) {

        log.info("Save user: {}", user);

        return userRepository.save(user);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED)
    @Override
    public void deleteById(@NonNull long id) {
        if (findById(id).isPresent()) {

            log.info("Delete user by id: {}", id);
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(
                    String.format(ExceptionMessageProvider.USER_ID_NOT_FOUND, id)
            );
        }
    }

    @Override
    public void deleteByEmail(@NonNull String email) {
        if (findByEmail(email).isPresent()) {

            log.info("Delete user by email: {}", email);
            userRepository.deleteByEmail(email);
        } else {
            throw new UserNotFoundException(
                    String.format(ExceptionMessageProvider.USER_EMAIL_NOT_FOUND, email)
            );
        }
    }

    @Override
    public boolean isEmailExist(@NonNull String email) {
        log.info("Check if user with email: {} exists", email);
        return userRepository.existsByEmail(email.toLowerCase());
    }

    @Override
    public boolean isUserExistById(@NonNull long id) {
        log.info("Check if user with id: {} exists", id);
        return userRepository.existsById(id);
    }
}
