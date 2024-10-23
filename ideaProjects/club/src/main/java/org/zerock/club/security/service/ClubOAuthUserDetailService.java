package org.zerock.club.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.club.config.CustomOAuth2User;
import org.zerock.club.entity.ClubMember;
import org.zerock.club.entity.ClubMemberRole;
import org.zerock.club.repository.ClubMemberRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuthUserDetailService extends DefaultOAuth2UserService {

    private final ClubMemberRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("--------------------------OAuth2--------------------");
        log.info("userRequest : " + userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("clientName : " + clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 권한 추가 꿀팁임
//        Set<GrantedAuthority> mappedAuthorities = new HashSet<>(oAuth2User.getAuthorities());
//        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        // 사용자 저장
        log.info("==================================");
        oAuth2User.getAttributes().forEach((k,v) -> {
            log.info(k + ":" + v);  // sub, name, given_name, family_name, picture, email, email_verified 출력
        });

        String email = null;

        if (clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }

        ClubMember member = saveSocialUser(email);

        // 권한추가 있을때 이거 사용
//        return new CustomOAuth2User(oAuth2User, mappedAuthorities);
        return oAuth2User;
    }

    private ClubMember saveSocialUser(String email) {

        Optional<ClubMember> result = repository.findByEmail(email, true);

        if(result.isPresent()) {
            return result.get();
        }

        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);
        repository.save(clubMember);
        return clubMember;
    }
}
