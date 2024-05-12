package com.springboot.study.api.common.config.auth;

import com.springboot.study.api.common.config.auth.dto.OAuthAttributes;
import com.springboot.study.api.common.config.auth.dto.SessionUser;
import com.springboot.study.datasource.user.User;
import com.springboot.study.datasource.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        /* 🐬 registrationId
            현재 로그인 진행 중인 서비스를 구분하는 코드
            여러 소셜 로그인 서비스를 연동할 때 네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("registrationId");
        System.out.println(registrationId);
        /*
        🐬 userNameAttributeName
            OAuth2 로그인 진행 시 키가 되는 필드값, Primary Key와 같은 의미
            구글의 경우 기본적으로 코드를 지원하지만, 네이버나 카카오 등은 기본 지원하지 않음
            이후 네이버 로그인과 구글 로그인을 동시 지원할 때 사용
        */
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        System.out.println("userNameAttributeName");
        System.out.println(userNameAttributeName);

        /*
        🐬 OAuthAttributes
            OAuth2UserService를 통해 가져온 OAuth2User의 attirbute를 담을 클래스
         */
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        /*
        🐬 SessionUser
            세션에 사용자 정보를 저장하기 위한 DTO 클래스
            User 클래스를 그대로 사용하면, User 클래스는 엔티티이기 때문에 언제 다른 엔티티와 관계가 형성될지 모른다.
            User 클래스에 직렬화 코드를 넣으면 직렬화 대상에 자식들까지 포함되어 성능 이슈, 부수 효과가 발생할 확률이 높아진다.
            그러므로 직렬화 기능을 가진 세션 DTO를 하나 추가로 만드는 것이 이후 운영 및 유지보수 때 많은 도움이 된다.
        */
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
