package com.bestStore.userService.mapper;

import com.bestStore.userService.model.User;
import com.common.lib.userModule.userDto.response.UserFullInfoResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = BasicUserInfoMapper.class)
public interface UserFullInfoMapper extends BaseMapper<User, UserFullInfoResponseDto> {

    @Override
    @Mapping(source = ".", target = "basicUserInfoResponseDto")
    UserFullInfoResponseDto toDto(User user);

    @Override
    @InheritInverseConfiguration
    User toEntity(UserFullInfoResponseDto dto);
}

