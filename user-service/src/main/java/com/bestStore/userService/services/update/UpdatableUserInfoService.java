package com.bestStore.userService.services.update;

import com.common.lib.userModule.userDto.request.UserUpdateRequestDto;
import com.common.lib.userModule.userDto.response.UserFullInfoResponseDto;
import org.springframework.lang.NonNull;

public interface UpdatableUserInfoService {

    UserFullInfoResponseDto updateUser(@NonNull UserUpdateRequestDto userUpdateRequestDto, long userId);

    void deleteUser(long id);

    void deleteUser(@NonNull String email);

}
