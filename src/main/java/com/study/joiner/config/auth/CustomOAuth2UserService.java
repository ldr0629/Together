package com.study.joiner.config.auth;

import com.study.joiner.config.auth.dto.OAuthAttributes;
import com.study.joiner.config.auth.dto.SessionUser;
import com.study.joiner.domain.user.SocialUser;
import com.study.joiner.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService(); // 지네릭스 써서 WhiteLabel 뜸
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 요청을 통해 등록 ID, 서비스 이름을 얻어 클라이언트 설정에 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        SocialUser socialUser = saveOrUpdate(attributes);

        httpSession.setAttribute("socialUser", new SessionUser(socialUser));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(socialUser.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private SocialUser saveOrUpdate(OAuthAttributes attributes) {
        SocialUser socialUser = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        int idx = attributes.getEmail().indexOf("@");
        socialUser.setNickName(attributes.getEmail().substring(0, idx));
        return userRepository.save(socialUser);
    }
}
