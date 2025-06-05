package com.example.microservices_auth.Oauth2;

import com.common.lib.authModule.authDto.OauthRegistrationCredentialsDto;
import com.common.lib.userModule.AuthProvider.AuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        String email = user.getAttribute("email");
        String name = user.getAttribute("name");


        ResponseEntity<Boolean> existsResponse = restTemplate.exchange(
                "http://USER-SERVICE/api/v1/user/existsByEmail",
                HttpMethod.POST,
                new HttpEntity<>(email),
                Boolean.class
        );

        if (!existsResponse.getBody()) {
            String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
            AuthProvider authProvider = AuthProvider.valueOf(provider.toUpperCase());

            OauthRegistrationCredentialsDto dto = new OauthRegistrationCredentialsDto(email, name, provider, authProvider);

            restTemplate.postForEntity("http://USER-SERVICE/api/v1/user/create", dto, Void.class);
        }

        return user;
    }
}

