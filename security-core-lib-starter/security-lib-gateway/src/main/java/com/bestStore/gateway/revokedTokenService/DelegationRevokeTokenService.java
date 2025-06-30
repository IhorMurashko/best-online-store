package com.bestStore.gateway.revokedTokenService;

import com.bestStore.gateway.revokedTokenService.tokenCache.RevokedTokenCache;
import org.springframework.lang.NonNull;
/**
 * Default implementation of {@link RevokeTokenService} that delegates token storage and lookup
 * to a {@link RevokedTokenCache} implementation.
 *
 * <p>
 * This layer abstracts token revocation mechanics and enables switching
 * storage backends (e.g., Redis, in-memory, database).
 * </p>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 */
public class DelegationRevokeTokenService implements RevokeTokenService {

    private final RevokedTokenCache revokedTokenCache;

    public DelegationRevokeTokenService(RevokedTokenCache revokedTokenCache) {
        this.revokedTokenCache = revokedTokenCache;
    }


    @Override
    public void revokeToken(@NonNull String... token) {
        revokedTokenCache.saveToken(token);
    }

    @Override
    public boolean isTokenRevoked(@NonNull String token) {
        return revokedTokenCache.isTokenPresent(token);
    }
}
