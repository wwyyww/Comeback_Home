package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.Report;
import java.util.List;
import java.util.Optional;

public interface ReportRepository {
    Report save(Report report);
    Optional<Report> findByReportId(Long report_id);
    Optional<Report> findByCaseId(String case_id);
    Optional<Report> findByUserId(String user_id);
    List<Report> findAll();
}
