package com.beststore.userservice.mapper;

import com.beststore.userservice.model.User;
import com.beststore.userservice.utils.UserFieldAdapter;
import com.common.lib.userModule.userDto.request.UserUpdateRequestDto;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = UserFieldAdapter.class)
public interface UpdatableUserInfoMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "email", source = "email", qualifiedByName = "toLower")
    @Mapping(target = "firstName", source = "firstName", qualifiedByName = "normalizeName")
    @Mapping(target = "lastName", source = "lastName", qualifiedByName = "normalizeName")
    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "sanitizePhoneNumber")
    @Mapping(target = "country", source = "country", qualifiedByName = "normalizeAddress")
    @Mapping(target = "city", source = "city", qualifiedByName = "normalizeAddress")
    @Mapping(target = "streetName", source = "streetName", qualifiedByName = "normalizeAddress")
    @Mapping(target = "zipCode", source = "zipCode", qualifiedByName = "normalizeZipCode")
    void updateEntityFromDto(UserUpdateRequestDto userUpdateRequestDto, @MappingTarget User user);

}
