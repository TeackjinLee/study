package org.zerock.club.security.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.club.config.CustomOAuth2User;

import java.util.HashSet;
import java.util.Set;

@Log4j2
@Service
public class ClubOAuthUserDetailService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("--------------------------OAuth2--------------------");
        log.info("userRequest : " + userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("clientName : " + clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("==================================");
        oAuth2User.getAttributes().forEach((k,v) -> {
            log.info(k + ":" + v);
        });

        Set<GrantedAuthority> mappedAuthorities = new HashSet<>(oAuth2User.getAuthorities());
        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomOAuth2User(oAuth2User, mappedAuthorities);
    }
}
