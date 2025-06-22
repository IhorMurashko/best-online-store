package com.example.microservices_auth.Oauth2;

import com.common.lib.authModule.authDto.OauthRegistrationCredentialsDto;
import com.common.lib.userModule.AuthProvider.AuthProvider;
import com.example.microservices_auth.webtoken.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        String email = user.getAttribute("email");
        String name = user.getAttribute("name");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-Email", email);
        HttpEntity<String> entity = new HttpEntity<>(headers);


        ResponseEntity<Boolean> existsResponse = restTemplate.exchange(
                "http://USER-SERVICE/api/v1/user/exist-by-email",
                HttpMethod.GET,
                entity,
                Boolean.class
        );

        if (Objects.equals(existsResponse.getBody(), false)) {
            String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
            AuthProvider authProvider = AuthProvider.valueOf(provider.toUpperCase());

            OauthRegistrationCredentialsDto dto = new OauthRegistrationCredentialsDto(email, name, provider, authProvider);

            headers.clear();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<OauthRegistrationCredentialsDto> request = new HttpEntity<>(dto, headers);

            ResponseEntity<HttpStatus> createUser = restTemplate.exchange(
                    "http://USER-SERVICE/api/v1/user/create-oauth",
                    HttpMethod.POST,
                    request,
                    HttpStatus.class);
        }

        return user;
    }
}

