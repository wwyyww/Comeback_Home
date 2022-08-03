package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.dto.ReportRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("select r from Report r where r.cases.case_id = :case_id")
    List<Report> findReportsCaseId(@Param("case_id") Long case_id);
    Report save(Report report);
    Optional<Report> findByReportId(Long report_id);
    Optional<Report> findByCaseId(String case_id);
    Optional<Report> findByUserId(String user_id);
    List<Report> findAll();
}
