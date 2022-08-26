package TheEarthGuard.ComeBackHome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ComeBackHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComeBackHomeApplication.class, args);
	}

}
