package com.bestStore.userService.services.update;

import com.bestStore.userService.exceptions.ExceptionMessageProvider;
import com.bestStore.userService.exceptions.UserNotFoundException;
import com.bestStore.userService.mapper.UpdatableUserInfoMapper;
import com.bestStore.userService.mapper.UserFullInfoMapper;
import com.bestStore.userService.model.User;
import com.bestStore.userService.services.userCrudService.UserCrudService;
import com.bestStore.userService.utils.UserFieldAdapter;
import com.common.lib.userModule.userDto.request.UserUpdateRequestDto;
import com.common.lib.userModule.userDto.response.UserFullInfoResponseDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdatableUserInfoServiceImpl implements UpdatableUserInfoService {

    private final UserCrudService userCrudService;
    private final UserFullInfoMapper userFullInfoMapper;
    private final UpdatableUserInfoMapper updatableUserInfoMapper;


    @Override
    public UserFullInfoResponseDto updateUser(@NonNull UserUpdateRequestDto userUpdateRequestDto, long userId) {


        User existingUser = userCrudService.findById(userId).orElseThrow(
                () -> new UserNotFoundException(
                        String.format(ExceptionMessageProvider.USER_ID_NOT_FOUND, userId)
                )
        );

        updatableUserInfoMapper.updateEntityFromDto(userUpdateRequestDto, existingUser);


        User saved = userCrudService.save(existingUser);

        return userFullInfoMapper.toDto(saved);
    }

    @Override
    public void deleteUser(long id) {

        boolean userExistById = userCrudService.isUserExistById(id);

        if (userExistById) {
            log.warn("User with id {} been deleted", id);
            userCrudService.deleteById(id);
        } else {
            log.warn("User with id {} was not found", id);
            throw new UserNotFoundException(
                    String.format(ExceptionMessageProvider.USER_ID_NOT_FOUND, id)
            );

        }


    }

    @Override
    public void deleteUser(@NonNull String email) {

        boolean userExistByEmail = userCrudService.isEmailExist(UserFieldAdapter.toLower(email));

        if (userExistByEmail) {
            log.warn("User with email {} been deleted", email);
            userCrudService.deleteByEmail(email);
        } else {
            log.warn("User with email {} was not found", email);
            throw new UserNotFoundException(
                    String.format(ExceptionMessageProvider.USER_EMAIL_NOT_FOUND, email)
            );

        }
    }
}
