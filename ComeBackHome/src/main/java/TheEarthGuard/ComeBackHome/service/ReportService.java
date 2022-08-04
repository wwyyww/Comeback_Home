package TheEarthGuard.ComeBackHome.service;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.ReportRequestDto;
import TheEarthGuard.ComeBackHome.repository.CaseRepository;
import TheEarthGuard.ComeBackHome.repository.ReportRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import TheEarthGuard.ComeBackHome.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final CaseRepository caseRepository;

    public ReportService(ReportRepository reportRepository, UserRepository userRepository, CaseRepository caseRepository) {

        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.caseRepository = caseRepository;
    }


    //실종 제보 등록
    @Transactional
    public Long UploadReport(Long user_id, Long case_id, ReportRequestDto reportObj){
        System.out.println("[CASE_SERVICE] reportObj.getWitness_time" + reportObj.getWitness_time());
        Optional<User> user = userRepository.findById(user_id);
        Case findCase=caseRepository.findByCaseId(case_id).orElseThrow(() ->
                new IllegalArgumentException("제보 작성 실패 : 존재하지 않는 게시글"));

        reportObj.setUser(user.get());
        reportObj.setCases(findCase);

        reportRepository.save(reportObj.toEntity());
        return reportObj.getId();
    }

//    private void validateDuplicateReport(Report report) {
//        // 수정 필요함 : 해당 유저가 해당 사건에 대해 등록한 적이 있는지 확인해야됨
//        reportRepository.findByCaseId(String.valueOf(report.getCases().getCase_id()))
//            .ifPresent(m -> {
//                throw new IllegalStateException("이미 존재하는 증언입니다.");
//            });
//    }


     // 전체 증언 조회
    public List<Report> findReports() {

        return reportRepository.findAll();
    }


    // 증언 하나 조회
//    public Optional<Report> findOne(String case_id) {
//
//        return reportRepository.findByCaseId(case_id);
//    }



}