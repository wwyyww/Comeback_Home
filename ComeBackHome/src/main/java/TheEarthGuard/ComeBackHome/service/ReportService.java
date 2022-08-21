package TheEarthGuard.ComeBackHome.service;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.FileEntity;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.domain.Warn;
import TheEarthGuard.ComeBackHome.dto.ReportRequestDto;
import TheEarthGuard.ComeBackHome.dto.WarnDto;
import TheEarthGuard.ComeBackHome.repository.CaseRepository;
import TheEarthGuard.ComeBackHome.repository.ReportRepository;
import TheEarthGuard.ComeBackHome.repository.UserRepository;
import TheEarthGuard.ComeBackHome.repository.WarnRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final CaseRepository caseRepository;
    private final WarnRepository warnRepository;
    private final FileService fileService;
    private Map<String, String> warnReasonList=new HashMap<>();


    public ReportService(ReportRepository reportRepository, UserRepository userRepository, CaseRepository caseRepository, WarnRepository warnRepository, FileService fileService) {

        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.caseRepository = caseRepository;
        this.warnRepository = warnRepository;
        this.fileService = fileService;
        warnReasonList.put("1", "허위신고");
        warnReasonList.put("2", "욕설/비하");
        warnReasonList.put("3", "낚시/놀람/도배");
        warnReasonList.put("4", "유출/사칭/사기");
        warnReasonList.put("5", "상업적 광고 및 판매");
        warnReasonList.put("6", "희롱 또는 괴롭힘");
        warnReasonList.put("7", "음란물/불건전한 만남 및 대화");
        warnReasonList.put("8", "정당/정치인 비하 및 선거운동");
    }


    //실종 제보 등록
    @Transactional
    public Long uploadReport(Long user_id, Long case_id, ReportRequestDto reportObj, List<MultipartFile> files) throws Exception {
        Report newReport = new Report();

        Optional<User> user = userRepository.findById(user_id);
        Case findCase=caseRepository.findByCaseId(case_id).orElseThrow(() ->
                new IllegalArgumentException("제보 작성 실패 : 존재하지 않는 게시글"));
        List<FileEntity> witPics = fileService.parseFileInfo(files);

        reportObj.setUser(user.get());
        reportObj.setCases(findCase);
        newReport = reportObj.toEntity();

        if(!witPics.isEmpty()) {
            System.out.println("[ReportService-witnessPics] 파일 있음!" + witPics);
            newReport.setWitnessPics(witPics);
        }

        reportRepository.save(newReport);
        return newReport.getId();
    }

    @Transactional
    public Long updateReport(Long user_id, Long report_id, ReportRequestDto reportObj) throws IllegalAccessException {
        Optional<User> user = userRepository.findById(user_id);
        Optional<Report> report = reportRepository.findById(report_id);

        Case findCase=caseRepository.findByCaseId(report.get().getCases().getCaseId()).orElseThrow(() ->
                new IllegalArgumentException("제보 수정 실패 : 존재하지 않는 게시글"));

        if (report.get().getUser().getId() != user.get().getId()) {
            throw new IllegalAccessException("제보 업데이트 실패 : 올바른 사용자가 아닙니다.");
        }

        report.get().updateReport(reportObj);
        return report_id;
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
    public void warnReport(Long id, User user, WarnDto form) {
        Optional<Report> report = reportRepository.findById(id);
        //1. 제보를 작성한 사용자 신고카운트 증가
        User reportUser=report.get().getUser();
        reportUser.updateWarnCount(reportUser.getWarning_cnt()+1);
        userRepository.saveAndFlush(reportUser);

        //2. warn 테이블에 저장
        Warn warn = Warn.builder()
                .warnSender(user)
                .warnReason(warnReasonList.get(form.getWarnReason()))
                .build();
        warnRepository.save(warn);
        report.get().getWarns().add(warn);
        report.get().setIs_alert(Boolean.TRUE);

    }


//    private void validateDuplicateReport(Report report) {
//        // 수정 필요함 : 해당 유저가 해당 사건에 대해 등록한 적이 있는지 확인해야됨
//        reportRepository.findByCaseId(String.valueOf(report.getCases().getCase_id()))
//            .ifPresent(m -> {
//                throw new IllegalStateException("이미 존재하는 증언입니다.");
//            });
//    }

//    public List<Report> getReportsListByFilter(){
//
//    }

    public Report getReportDetail(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 제보입니다."));
    }

    public List<Report> getReportsListByCase(Case selectCase) {
        return reportRepository.findAllByCasesOrderByWitnessTimeDesc(selectCase).orElseThrow(() -> new RuntimeException("존재하지 않는 사건입니다."));

    }

    //사용자가 작성한 제보 조회
    public List<Report> getReportsListByUser(User user) {
        return reportRepository.findAllByUserOrderByWitnessTimeDesc(user).orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
    }

    //전체 증언 조회
    public List<Report> getReports() {
        return reportRepository.findAll();
    }




    public Optional<List<Report>> getByFilters(String area, LocalDate start, LocalDate end){
        Optional<List<Report>> reportList = Optional.empty();
        System.out.println(area + start + end);

        if(!Objects.equals(area, "전체") && start != null ){
            System.out.println("필터링 전부 선택");
            reportList = reportRepository.findByWitnessRegionAndWitnessTimeBetween(area, start.atStartOfDay(), end.atTime(LocalTime.MAX));
        } else if (!Objects.equals(area, "전체") && start == null && end == null) {
            System.out.println("필터링 지역만 선택");
            reportList = reportRepository.findByWitnessRegion(area);
        } else if (Objects.equals(area, "전체") && start != null && end != null){
            System.out.println("필터링 시간만 선택");
            reportList = reportRepository.findByWitnessTimeBetween(start.atStartOfDay(), end.atTime(LocalTime.MAX));
        } else if (Objects.equals(area, "전체") && start == null && end == null){
            System.out.println("필터링 전부 선택 안함");
            reportList = reportRepository.findAllByOrderByWitnessTimeDesc();
        }
        return reportList;
    }


}