package com.matthew.project.api_gateway.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/*
creates a signed JWT for the given user.
It includes issuer, username, and roles as claims.
The token is valid for a predefined duration and used for authentication.
 */

@Service
public class JwtService {

    private static final String SECRET = "AFF43B35FBDA028DCE5668FEFE60AD2B628A71D8F68D60EBA634D13BED41287801E9B2BF6D7D3F9D354E0BA0A85DA591326EBF5A9B99338D06FFC4586DB76E73";
    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "https://teamchallengeproject.com");
        claims.put("X-User-Name", userDetails.getUsername());
        claims.put("X-User-Roles", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey(){
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    public String extractId(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.get("userId", String.class);
    }

    public Claims getClaims(String jwt) {
        Claims claims = Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        return claims;
    }

    public String extractRoles(String jwt) {
        Claims claims = getClaims(jwt);
        String rolesObject = claims.get("roles").toString();
        return rolesObject;
    }

    public boolean isTokenValid(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }
}
