package study.metacodingsecurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import study.metacodingsecurity.config.oauth.PrincipalOauth2UserService;

// SecurityAutoConfiguration.class 설정파일 참조
// 스프링 시큐리티가 의존성으로 등록되어 있으면(SecurityAutoConfiguration),
// DefaultAuthenticationEventPublisher가 @Bean으로 등록된다.
// SpringBootWebSecurityConfiguration -> WebSecurityConfigurerAdapter -> configure(HttpSecurity http)
// 출처: https://ict-nroo.tistory.com/118?category=813642 [개발자의 기록습관]

@RequiredArgsConstructor
@EnableWebSecurity  // 스프링 시큐리티 필터가 스프링 필터체인에 등록이됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)  // `@Secured` 어노테이션 활성화, `@PreAuthorize`,`@PostAuthorize` 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
    private final PrincipalOauth2UserService principalOauth2UserService;

    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .headers().frameOptions().disable()
//                .and()
                .authorizeRequests()
                .antMatchers("/user/**").authenticated()  // 인증만 되면 들어갈 수 있는 주소!!
                .antMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")  // 권한 없을시 "/login" 으로 이동
//                .usernameParameter("username2")
                .loginProcessingUrl("/login")  // login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌.
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm")  // 구글 로그인이 완료된 뒤의 후처리가 필요함.  Tip. 구글로그인이 완료가 되면, Code를 받는게 아니라(Code는 이미 받음) `엑세스토큰+사용자프로필정보`를 한 번에 받음.
                .userInfoEndpoint()
                .userService(principalOauth2UserService);  // `principalOauth2UserService` 의 `loadUser`에서 후처리
                // 1. code받기(인증)
                // 2. code로 엑세스토큰 받기(권한)
                // 3. 사용자프로필 정보를 가져오고
                // 4-1. 그 정보를 토대로 회원가입을 자동으로 진행시키도 함.
                // 4-2. (이메일, 전화번호, 이름, 아이디) 쇼핑몰 -> (집주소), 백화점몰 -> (VIP등급, 일반등급) : 이렇게 필요한 추가적인 정보가 있으면 별도의 회원가입도 진행.
    }
}