package TheEarthGuard.ComeBackHome;

import TheEarthGuard.ComeBackHome.service.UserService;
import TheEarthGuard.ComeBackHome.respoitory.*;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.ReportService;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;
    private final EntityManager em;

    public SpringConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean
    public ReportService reportService() {
        return new ReportService(reportRepository());
    }

    @Bean
    public CaseService caseService() {
        return new CaseService(caseRepository());
    }

//    @Bean
//    public UserService memberService() {
//        return new UserService(memberRepository());
//    }
    @Bean
    public ReportRepository reportRepository() {
        return new JpaReportRepository(em);
    }
    @Bean
    public CaseRepository caseRepository() {
        return new JpaCaseRepository(em);
    }

//    @Bean
//    public UserRepository memberRepository() {
//        return new JpaUserRepository(em);
//    }

}
