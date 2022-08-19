package TheEarthGuard.ComeBackHome;

import TheEarthGuard.ComeBackHome.repository.CaseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication
public class ComeBackHomeApplication {


	public static void main(String[] args) {

		SpringApplication.run(ComeBackHomeApplication.class, args);
	}

}
