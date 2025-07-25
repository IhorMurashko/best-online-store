package com.bestStore.userservice.services.billinginfo;

import com.bestStore.userservice.model.BillingInfo;
import java.util.List;
import java.util.Optional;

import com.common.lib.userModule.userDto.request.BillingInfoUpdateRequestDto;
import lombok.NonNull;

public interface BillingInfoService {
    BillingInfo save(@NonNull BillingInfo billingInfo);
    Optional<BillingInfo> findById(@NonNull Long id);
    void delete(@NonNull Long id);
    List<BillingInfo> findAllByUserId(@NonNull Long userId);
    BillingInfo update(BillingInfoUpdateRequestDto requestDto, @NonNull Long id);
}
