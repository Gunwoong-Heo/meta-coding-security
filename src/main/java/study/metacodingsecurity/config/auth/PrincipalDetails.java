package study.metacodingsecurity.config.auth;

// 시큐리티가 '/login' 주소 요청이 오면 낚아채서 로그인을 진행시킴
// 로그인 진행이 완료가 되면 `시큐리티 session`을 만들어줌.(Security ContextHolder)
// `시큐리티 session`에 들어갈 수 있는 객체는 정해져있음. 
// 오브젝트 타입 <= Authentication 타입 객체
// User오브젝트타입 <= UserDetails 타입 객체

// Security Session <= Authentication <= UserDetails(PrincipalDetails)

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import study.metacodingsecurity.domain.User;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 유저의 권한을 리턴하는 곳!!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collet = new ArrayList<>();
        collet.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collet;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 우리 사이트에서 1년 동안 회원이 로그인을 안하면!! 휴먼 계정으로 하기로 한다고 하면.
        // `현재시간 - 마지막로그인시간`이 1년을 초과하면 return false;
        return true;
    }
}