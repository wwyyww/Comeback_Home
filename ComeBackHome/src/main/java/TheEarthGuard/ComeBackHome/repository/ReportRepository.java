package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.Report;
import java.util.List;
import java.util.Optional;

public interface ReportRepository {
    Report save(Report report);
    Optional<Report> findByReportId(Long reportId);
    Optional<Report> findByCaseId(String caseId);
    Optional<Report> findByUserId(String userId);
    List<Report> findAll();
}
