package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

//    @Query("select r from Report r where r.cases.case_id = :case_id")
//    List<Report> findReportsCaseId(@Param("case_id") Long case_id);

//    List<Report> findAllByCaseId(Long id);
//    Report save(Report report);
//    Optional<Report> findByReportId(Long report_id);
//    Optional<Report> findByCaseId(String case_id);
//    Optional<Report> findByUserId(String user_id);
//    List<Report> findAll();
}
