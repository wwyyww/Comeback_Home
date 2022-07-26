package TheEarthGuard.ComeBackHome.service;

import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.repository.ReportRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

@Transactional
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * 증언 남기기
     */
    public Long testify(Report report) {
        validateDuplicateReport(report); //중복 증언 검증
        reportRepository.save(report);
        return report.getReport_id();
    }

    private void validateDuplicateReport(Report report) {
        // 수정 필요함 : 해당 유저가 해당 사건에 대해 등록한 적이 있는지 확인해야됨
        reportRepository.findByCaseId(report.getCase_id())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 증언입니다.");
            });
    }

    /**
     * 전체 증언 조회
     */
    public List<Report> findReports() {

        return reportRepository.findAll();
    }

    /**
     * 증언 하나 조회
     */
    public Optional<Report> findOne(String case_id) {

        return reportRepository.findByCaseId(case_id);
    }

    /*
    실종 제보 등록
     */
    public Long UploadReport(Report reportObj){
        reportRepository.save(reportObj);
        return reportObj.getReport_id();
    }

}