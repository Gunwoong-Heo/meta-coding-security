package study.metacodingsecurity.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    // 구글로부터 받은 userRequest 데이터를 후처리 하는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest = " + userRequest);
        System.out.println("userRequest.getAccessToken() = " + userRequest.getAccessToken());
        System.out.println("userRequest.getAccessToken().getTokenValue() = " + userRequest.getAccessToken().getTokenValue());
        System.out.println("userRequest.getClientRegistration() = " + userRequest.getClientRegistration());
        System.out.println("super.loadUser(userRequest).getAttributes() = " + super.loadUser(userRequest).getAttributes());

        // 회원가입을 강제로 진행해볼 예정
        return super.loadUser(userRequest);
    }
}