package study.metacodingsecurity.domain;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role; //ROLE_USER, ROLE_ADMIN

    private String provider;
    private String providerId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    // username = "google_113594258346664706643"
    // password = "암호화(ungk)"  // Oauth 사용하면 password를 사용할 것은 아니라서 아무거나 넣어도 상관은 없음
    // email = "gunwoong1129@gmail.com"
    // provider = "google"
    // providerId = "113594258346664706643"

}