package com.bestStore.userservice.services.billinginfo;

import com.bestStore.userservice.exceptions.BillingInfoNotFoundException;
import com.bestStore.userservice.mapper.UpdateBillingInfoMapper;
import com.bestStore.userservice.model.BillingInfo;
import com.bestStore.userservice.repositories.BillingInfoRepository;
import com.common.lib.userModule.userDto.request.BillingInfoUpdateRequestDto;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillingInfoServiceImpl implements BillingInfoService{
    private final BillingInfoRepository billingRepo;
    private final UpdateBillingInfoMapper updateBillingInfoMapper;


    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED)
    @Override
    public BillingInfo save(@NonNull BillingInfo billingInfo) {
        log.info("Save billingInfo: {}", billingInfo);
        return billingRepo.save(billingInfo);
    }

    @Override
    public Optional<BillingInfo> findById(@NonNull Long id) {
        log.info("Find billingInfo by id: {}", id);
        return billingRepo.findById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED)
    @Override
    public void delete(@NonNull Long id) {
        log.info("delete billingInfo with id: {}", id);
        billingRepo.deleteById(id);
    }

    @Override
    public List<BillingInfo> findAllByUserId(@NonNull Long userId) {
        log.info("find all billingInfo with user id: {}", userId);
        return billingRepo.findAllByUserId(userId);
    }

    @Override
    public BillingInfo update(BillingInfoUpdateRequestDto requestDto, @NonNull Long id) {
        log.info("update billingInfo with id: {}", id);
        BillingInfo billingInfo = billingRepo.findById(id).orElseThrow(
                () -> new BillingInfoNotFoundException("No such billing info with id " + id)
        );
        updateBillingInfoMapper.updateEntityFromDto(requestDto, billingInfo);
        return billingRepo.save(billingInfo);
    }
}
