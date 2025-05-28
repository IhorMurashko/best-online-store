package com.bestStore.userService.mapper;

import com.bestStore.userService.model.User;
import com.common.lib.userModule.userDto.response.BasicUserInfoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BasicUserInfoMapper extends BaseMapper<User, BasicUserInfoResponseDto> {

}
