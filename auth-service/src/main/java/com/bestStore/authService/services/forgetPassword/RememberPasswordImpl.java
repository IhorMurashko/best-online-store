package com.bestStore.authService.services.forgetPassword;

import com.bestStore.auth.RememberPasswordServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RememberPasswordImpl implements RememberPassword {

    private final RememberPasswordServiceGrpc.RememberPasswordServiceBlockingStub blockingStub;



    @Override
    public boolean resetPassword(String email) {
        return false;
    }
}
