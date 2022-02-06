package study.metacodingsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MetaCodingSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetaCodingSecurityApplication.class, args);
	}

}
