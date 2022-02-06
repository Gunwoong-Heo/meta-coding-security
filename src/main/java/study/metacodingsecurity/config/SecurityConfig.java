package study.metacodingsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// SecurityAutoConfiguration.class 설정파일 참조
// 스프링 시큐리티가 의존성으로 등록되어 있으면(SecurityAutoConfiguration),
// DefaultAuthenticationEventPublisher가 @Bean으로 등록된다.
// SpringBootWebSecurityConfiguration -> WebSecurityConfigurerAdapter -> configure(HttpSecurity http)
// 출처: https://ict-nroo.tistory.com/118?category=813642 [개발자의 기록습관]

@EnableWebSecurity  // 스프링 시큐리티 필터가 스프링 필터체인에 등록이됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)  // `@Secured` 어노테이션 활성화, `@PreAuthorize`,`@PostAuthorize` 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
                .antMatchers("/manager/**").hasAnyRole("ADMIN","MANAGER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")  // 권한 없을시 "/login" 으로 이동
//                .usernameParameter("username2")
                .loginProcessingUrl("/login")  // login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌.
                .defaultSuccessUrl("/");
    }
}