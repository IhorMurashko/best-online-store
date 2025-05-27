package com.bestStore.userService.services.update;

import com.common.lib.userModule.userDto.request.UserUpdateRequestDto;
import com.common.lib.userModule.userDto.response.BasicUserInfoResponse;
import org.springframework.lang.NonNull;

public interface UpdatableUserInfoService {

    BasicUserInfoResponse updateUser(@NonNull UserUpdateRequestDto userUpdateRequestDto,@NonNull Long userId);

    void deleteUser(@NonNull Long id);


}
