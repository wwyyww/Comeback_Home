package TheEarthGuard.ComeBackHome.service;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.domain.Warn;
import TheEarthGuard.ComeBackHome.dto.ReportRequestDto;
import TheEarthGuard.ComeBackHome.repository.CaseRepository;
import TheEarthGuard.ComeBackHome.repository.ReportRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import TheEarthGuard.ComeBackHome.repository.UserRepository;
import TheEarthGuard.ComeBackHome.repository.WarnRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final CaseRepository caseRepository;
    private final WarnRepository warnRepository;

    public ReportService(ReportRepository reportRepository, UserRepository userRepository, CaseRepository caseRepository, WarnRepository warnRepository) {

        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.caseRepository = caseRepository;
        this.warnRepository = warnRepository;
    }


    //실종 제보 등록
    @Transactional
    public Long uploadReport(Long user_id, Long case_id, ReportRequestDto reportObj){
        Optional<User> user = userRepository.findById(user_id);
        Case findCase=caseRepository.findByCaseId(case_id).orElseThrow(() ->
                new IllegalArgumentException("제보 작성 실패 : 존재하지 않는 게시글"));

        reportObj.setUser(user.get());
        reportObj.setCases(findCase);

        reportRepository.save(reportObj.toEntity());
        return reportObj.getId();
    }

    @Transactional
    public Long updateReport(Long user_id, Long case_id, ReportRequestDto reportObj) throws IllegalAccessException {
        Optional<User> user = userRepository.findById(user_id);
        Case findCase=caseRepository.findByCaseId(case_id).orElseThrow(() ->
                new IllegalArgumentException("제보 수정 실패 : 존재하지 않는 게시글"));
        Optional<Report> report = reportRepository.findById(reportObj.getId());

        if (report.get().getUser().getId() != user.get().getId()) {
            throw new IllegalAccessException("제보 업데이트 실패 : 올바른 사용자가 아닙니다.");
        }
        reportObj.setUser(user.get());
        reportObj.setCases(findCase);
        Report updateReport = reportObj.toEntity();
        updateReport.setCreatedTime(report.get().getCreatedTime());
        reportRepository.save(updateReport);

        return reportObj.getId();
    }

    @Transactional
    public void deleteReport(Long id, User user) {
        Optional<Report> report = reportRepository.findById(id);
        if (user.getId() == report.get().getUser().getId()) {
            reportRepository.deleteById(id);
        }else{
            new IllegalArgumentException("제보 삭제 실패 : 사용자가 일치하지 않음");
        }
    }

    @Transactional
    public void warnReport(Long id, User user) {
        Optional<Report> report = reportRepository.findById(id);
        //1. 제보를 작성한 사용자 신고카운트 증가
        User reportUser=report.get().getUser();
        reportUser.updateWarnCount(reportUser.getWarning_cnt()+1);
        userRepository.saveAndFlush(reportUser);

        //2. warn 테이블에 저장
        Warn warn = new Warn();
        warn.setReports(report.get());
        warn.setWarnSender(user);
        warn.setWarnReason("신고 이유");
        warnRepository.save(warn);

    }


//    private void validateDuplicateReport(Report report) {
//        // 수정 필요함 : 해당 유저가 해당 사건에 대해 등록한 적이 있는지 확인해야됨
//        reportRepository.findByCaseId(String.valueOf(report.getCases().getCase_id()))
//            .ifPresent(m -> {
//                throw new IllegalStateException("이미 존재하는 증언입니다.");
//            });
//    }

    public Report getReportDetail(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 제보입니다."));
    }

    public List<Report> getReportsListByCase(Case selectCase) {
        return reportRepository.findAllByCases(selectCase).orElseThrow(() -> new RuntimeException("존재하지 않는 사건입니다."));

    }

    //사용자가 작성한 제보 조회
    public List<Report> getReportsListByUser(User user) {
        return reportRepository.findAllByUser(user).orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
    }

    //전체 증언 조회
    public List<Report> findReports() {
        return reportRepository.findAll();
    }

}