package TheEarthGuard.ComeBackHome;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;
    private final EntityManager em;

    public SpringConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public ReportService reportService() {
//        return new ReportService(reportRepository());
//    }

//    @Bean
//    public CaseService caseService() {
//        return new CaseService((CaseRepository) CaseCustomRepository(), new FileHandler());
//    }

//    @Bean
//    public UserService memberService() {
//        return new UserService(memberRepository());
//    }
//    @Bean
//    public ReportRepository reportRepository() {
//        return new JpaReportRepository(em);
//    }
//    @Bean
//    public CaseCustomRepository CaseCustomRepository() {
//        return new CaseCustomImpl(em);
//    }

//    @Bean
//    public UserRepository memberRepository() {
//        return new JpaUserRepository(em);
//    }

}
