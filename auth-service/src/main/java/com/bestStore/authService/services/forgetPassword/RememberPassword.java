package com.bestStore.authService.services.forgetPassword;

@FunctionalInterface
public interface RememberPassword {
    public boolean resetPassword(String email);
}
