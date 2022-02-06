package study.metacodingsecurity.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.metacodingsecurity.domain.User;
import study.metacodingsecurity.repository.UserRepository;

// 시큐리티 설정에서 loginProcessingUrl("/login");
// '/login' 요청이 오면 자동으로 `UserDetailsService` 타입으로 Ioc 되어 있는 `loadUserByUsername` 함수가 실행
// Security Session(내부에 Authentication(내부에 UserDetails(PrincipalDetails)))
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Security Session <= Authentication <= UserDetails(PrincipalDetails)
    @Override
    // post로 넘어오는 필드의 name을 parameter로 받음.
    // 다른 이름으로 하고 싶으면, `SecurityConfig`의 `configure 메소드` 에서 `usernameParameter("username2")` 이런식으로 설정해야함.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}