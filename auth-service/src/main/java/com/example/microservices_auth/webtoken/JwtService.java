package com.example.microservices_auth.webtoken;

<<<<<<< HEAD
import com.example.microservices_auth.model.CustomUserDetails;
=======
import com.common.lib.authModule.authDto.BasicUserAuthenticationResponseDto;
>>>>>>> main
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
<<<<<<< HEAD
import java.security.Key;
=======
>>>>>>> main
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

<<<<<<< HEAD
=======
/*
Generates a JWT token for the authenticated user.
Includes user ID, roles, and standard claims like issuer, subject, issued time, and expiration.
The token is valid for 30 minutes and signed using a secret key.
*/

>>>>>>> main
@Service
public class JwtService {

    private static final String SECRET = "AFF43B35FBDA028DCE5668FEFE60AD2B628A71D8F68D60EBA634D13BED41287801E9B2BF6D7D3F9D354E0BA0A85DA591326EBF5A9B99338D06FFC4586DB76E73";
    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(UserDetails userDetails) {
        Map<String, String> claims = new HashMap<>();
<<<<<<< HEAD
        claims.put("iss", "https://teamchallengeproject.com");
        CustomUserDetails user = (CustomUserDetails) userDetails;

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        claims.put("roles", String.join(",", roles));
        return Jwts.builder()
                .claims(claims)
                .subject(user.getId().toString())
=======
        claims.put("iss", "https://teamchallengeproject-bestonlinestore.com");
        BasicUserAuthenticationResponseDto user = new BasicUserAuthenticationResponseDto(userDetails);
        Long userId = user.getId();
        claims.put("userId", userId.toString());

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", String.join(",", roles));

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
>>>>>>> main
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }

<<<<<<< HEAD
    private SecretKey generateKey(){
=======
    private SecretKey generateKey() {
>>>>>>> main
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    private Claims getClaims(String jwt) {
        Claims claims = Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        return claims;
    }

    public boolean isTokenValid(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }
}
