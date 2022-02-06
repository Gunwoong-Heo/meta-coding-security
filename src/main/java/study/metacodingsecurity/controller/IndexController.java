package study.metacodingsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import study.metacodingsecurity.domain.User;
import study.metacodingsecurity.repository.UserRepository;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"","/"})
    public String Index() {
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        System.out.println("user = " + user);
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);  // 회원가입잘되나, 비밀번호:1234 => 시큐리티로 로그인 할 수 없음.(패스워드 암호화가 안 되어있기 때문에) -> `BCryptPasswordEncoder`를 @Bean 으로 등록하고 주입받아서 패스워드 암호화
        return "redirect:/loginForm";  // `redirect`를 붙이면 "/loginForm"에 해당하는 함수 호출 -> `public String loginForm()`
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")  // data()메소드가 실행되기 직전에 실행됨.
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }

}