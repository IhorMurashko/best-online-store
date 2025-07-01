package com.bestStore.userservice.repositories;

import com.bestStore.userservice.model.BillingInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingInfoRepository extends JpaRepository<BillingInfo, Long> {
    List<BillingInfo> findAllByUserId(Long userId);
}
