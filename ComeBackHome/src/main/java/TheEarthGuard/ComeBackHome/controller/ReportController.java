package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.CaseResponseDto;
import TheEarthGuard.ComeBackHome.dto.ReportRequestDto;
import TheEarthGuard.ComeBackHome.dto.ReportResponseDto;
import TheEarthGuard.ComeBackHome.dto.SearchFormDto;
import TheEarthGuard.ComeBackHome.security.CurrentUser;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.ReportService;
import TheEarthGuard.ComeBackHome.service.UserService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;


@Slf4j
@Controller
@SessionAttributes({"reportForm"})
public class ReportController {
    private final ReportService reportService;
    private UserService userService;
    private CaseService caseService;

    @Autowired
    @SuppressFBWarnings(justification = "Generated code")
    public ReportController(ReportService reportService, UserService userService, CaseService caseService) {

        this.reportService = reportService;
        this.userService = userService;
        this.caseService = caseService;

    }

    //처음 제보 등록할 때
    @GetMapping(value = "/reports/new/{id}")
    public String createForm(Model model, @PathVariable("id") Long id, @CurrentUser User user) throws IllegalAccessException {
        ReportRequestDto reportDto = new ReportRequestDto();
        if (user == null) {
            return "redirect:/cases";
        }
        Optional<Case> caseDto = caseService.findCase(id);
        reportDto.setCases(caseDto.get());
        model.addAttribute("reportForm", reportDto);

        return "reports/createReportForm";
    }

    //제보 제출
    @PostMapping(value = "/reports/new/{id}")
    public String createReport(@Valid @ModelAttribute ReportRequestDto form, @PathVariable("id") Long id, @CurrentUser User user,Errors errors) throws Exception {
        if (errors.hasErrors()) {
            System.out.println("ERROR!!!!!!!!");
            return "/";
        }

        User currentUser = userService.findByEmail(user.getEmail());
        if (currentUser != null) {
            reportService.uploadReport(currentUser.getId(), id, form, form.getWitnessPics());
        }else{
            return "/users/login";
        }

        return "redirect:/cases";
    }

    @GetMapping(value = "/reports/reportList/{id}")
    public String caseReportList(Model model, @PathVariable("id") Long id, @PageableDefault(size=12) Pageable pageable,@RequestParam(value="page", defaultValue="0") int page, @CurrentUser User user, HttpServletRequest request){
        System.out.println("redirect:  " + RequestContextUtils.getInputFlashMap(request));
        Optional<List<Report>> reportList = Optional.empty();

        Optional<Case> caseEntity = caseService.findCase(id);
        if(caseEntity.isPresent()) {
            caseService.countHitCase(caseEntity.get().getCaseId()); // hit ++
            model.addAttribute("case", new CaseResponseDto(caseEntity.get(), caseEntity.get().getUser()));
        }


        if (RequestContextUtils.getInputFlashMap(request) != null){
            reportList = (Optional<List<Report>>) RequestContextUtils.getInputFlashMap(request).values().stream().collect(Collectors.toList()).get(0);
            if (reportList.isPresent()){
                System.out.println("값이 있음");
            } else {
                System.out.println("값이 없음");
            }

        } else {
            System.out.println("nono");
            if (user != null && (user.getId().equals(caseEntity.get().getUser().getId()))) {
                //List<Report> reports = reportService.getReportsListByCase(caseEntity.get());
                reportList = Optional.ofNullable(reportService.getReportsListByCase(caseEntity.get()));
                //model.addAttribute("reports", reports);
            }else{
                return "redirect:/";
            }
        }
        if(reportList.isPresent()) {
            System.out.println(reportList.get());
            List<ReportResponseDto> reportDtoList = reportList.get().stream().map(
                    ReportEntity -> new ReportResponseDto(ReportEntity, ReportEntity.getUser())
            ).collect(Collectors.toList());

            // 페이징 변환 작업
            final int startPage = (int)pageable.getOffset();
            final int endPage = Math.min((startPage + pageable.getPageSize()), reportDtoList.size());
            Page<ReportResponseDto> pagingDtoList = new PageImpl<>(reportDtoList.subList(startPage, endPage), pageable, reportDtoList.size());

            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("reports", pagingDtoList);
        } else {
            System.out.println("없음");
        }

        return "/reports/reportList";

    }


    @PostMapping(value = "/reports/reportList/{id}/submit")
    public String showCaseReportList(@ModelAttribute SearchFormDto form, Model model, @PathVariable("id") Long id, @PageableDefault(size=12) Pageable pageable,@RequestParam(value="page", defaultValue="0") int page, RedirectAttributes redirectAttributes){
        Optional<List<Report>> reportList = Optional.empty();
        String area = form.getMissing_area2();
        LocalDate start = form.getMissing_time_start();
        LocalDate end = form.getMissing_time_end();
        System.out.println(id + area + start + end);
        reportList = reportService.getByFilters(area, start, end);
        if(reportList.isPresent()) {
            System.out.println(reportList.get());
        } else {
            System.out.println("없음");
        }
        redirectAttributes.addFlashAttribute("searchReport", reportList);
        //return "/cases/caseDetail";
        return "redirect:/reports/reportList/{id}";
    }
    @GetMapping(value = "/mypage/reports")
    public String myReportList(Model model, @CurrentUser User user, @PageableDefault(size=12) Pageable pageable,@RequestParam(value="page", defaultValue="0") int page) {
        List<Report> reports = reportService.getReportsListByUser(user);

        List<ReportResponseDto> reportDtoList = reports.stream().map(

            ReportEntity -> new ReportResponseDto(ReportEntity, ReportEntity.getUser())
        ).collect(Collectors.toList());

        // 페이징 변환 작업
        final int startPage = (int)pageable.getOffset();
        final int endPage = Math.min((startPage + pageable.getPageSize()), reportDtoList.size());
        Page<ReportResponseDto> pagingDtoList = new PageImpl<>(reportDtoList.subList(startPage, endPage), pageable, reportDtoList.size());

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("reports", pagingDtoList);
        model.addAttribute("user", user);

        return "reports/myReportList";
    }

    //제보 상세보기
    @GetMapping(value = "/reports/detail/{id}")
    public String reportDetail(Model model, @PathVariable("id") Long id, @CurrentUser User user) {
        Report reportDto = reportService.getReportDetail(id);

        ReportResponseDto responseDto = new ReportResponseDto(reportDto, reportDto.getUser());
        model.addAttribute("report", responseDto);
        model.addAttribute("user", user);
        return "reports/reportDetail";
    }

    //제보 삭제하기
    @GetMapping(value = "/reports/delete/{id}")
    public String deleteReport(@PathVariable("id") Long id, @CurrentUser User user) {
        Report report = reportService.getReportDetail(id);
        if (user.getId().equals(report.getUser().getId())) {
            reportService.deleteReport(id, user);
            return "redirect:/cases";
        }
        return "redirect:/cases";
    }

    //제보 수정하기 (첫 화면)
    @GetMapping(value = "/reports/update/{id}")
    public String updateReportForm(Model model, @PathVariable("id") Long id, @CurrentUser User user) {
        Report report = reportService.getReportDetail(id);
        ReportResponseDto responseDto = new ReportResponseDto(report, user);
        if (user.getId().equals(report.getUser().getId())) {
            model.addAttribute("reportForm", responseDto);
            return "/reports/reportUpdate";
        }

        return "redirect:/cases";
    }

    //제보 수정하기 (제출)
    @PostMapping(value = "/reports/update/{id}")
    public String updateReport(@Valid @ModelAttribute ReportRequestDto form, @PathVariable("id") Long id,
                               @CurrentUser User user, Errors errors) throws IllegalAccessException {
        if (errors.hasErrors()) {
            log.info("error!!");
            return "redirect:/cases";
        }

        reportService.updateReport(user.getId(), id, form);
        return "redirect:/reports/detail/{id}";
    }


//    //제보 신고하기
//    @PostMapping(value = "/reports/warn/{id}")
//    public String reportWarn(Model model, @PathVariable("id") Long id, @CurrentUser User user) {
//        reportService.warnReport(id, user);
//        log.info("신고하기 버튼 눌림");
//        model.addAttribute("report", reportService.getReportDetail(id));
//        model.addAttribute("user", user);
//        return "redirect:/reports/detail/{id}";
//    }



}