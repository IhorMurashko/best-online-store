package com.beststore.userservice.mapper;

import com.beststore.userservice.model.BillingInfo;
import com.common.lib.userModule.userDto.request.BillingInfoUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UpdateBillingInfoMapper {

    void updateEntityFromDto(BillingInfoUpdateRequestDto billingInfoUpdateRequestDto, @MappingTarget BillingInfo billingInfo);
}
