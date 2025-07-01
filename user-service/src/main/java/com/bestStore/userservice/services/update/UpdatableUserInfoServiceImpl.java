package com.bestStore.userservice.services.update;

import com.bestStore.userservice.exceptions.ExceptionMessageProvider;
import com.bestStore.userservice.exceptions.UserNotFoundException;
import com.bestStore.userservice.mapper.UpdatableUserInfoMapper;
import com.bestStore.userservice.mapper.UserFullInfoMapper;
import com.bestStore.userservice.model.User;
import com.bestStore.userservice.services.userCrudService.UserCrudService;
import com.common.lib.userModule.userDto.request.UserUpdateRequestDto;
import com.common.lib.userModule.userDto.response.BasicUserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdatableUserInfoServiceImpl implements UpdatableUserInfoService {

    private final UserCrudService userCrudService;
    private final UserFullInfoMapper userFullInfoMapper;
    private final UpdatableUserInfoMapper updatableUserInfoMapper;


    @Override
    public BasicUserInfoResponse updateUser(@NonNull UserUpdateRequestDto userUpdateRequestDto, @NonNull Long userId) {


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
    public void deleteUser(@NonNull Long id) {
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
}
