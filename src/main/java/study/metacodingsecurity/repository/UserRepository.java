package study.metacodingsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.metacodingsecurity.domain.User;

//Repository라는 어노테이션이 없어도 Ioc됨, JpaRepository를 상속했기 때문
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}