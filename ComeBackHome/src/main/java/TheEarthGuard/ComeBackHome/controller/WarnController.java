package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.CaseResponseDto;
import TheEarthGuard.ComeBackHome.dto.WarnDto;
import TheEarthGuard.ComeBackHome.security.CurrentUser;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.ReportService;
import TheEarthGuard.ComeBackHome.service.UserService;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class WarnController {
    private final CaseService caseService;
    private UserService userService;
    private final ReportService reportService;


    public WarnController(CaseService caseService, UserService userService, ReportService reportService) {
        this.caseService = caseService;
        this.userService = userService;
        this.reportService = reportService;
    }

    // 제보 신고 페이지 띄우기
    @GetMapping(value = "/reports/detail/{id}/warn")
    public String createReportWarn(Model model, @PathVariable("id") Long id, @CurrentUser User user) {
        Report reportDto = reportService.getReportDetail(id);
        model.addAttribute("report", reportDto);
        model.addAttribute("user", user);
        return "/warn/createReportWarn";
    }

    // 제보 신고 등록
    @PostMapping(value = "/reports/detail/{id}/warn/submit")
    public String reportReportWarn(@Valid @ModelAttribute WarnDto form, Model model, @PathVariable("id") Long id, @CurrentUser User user) {

        log.info("신고하기 버튼 눌림");
        User currentUser = userService.findByEmail(user.getEmail());
        if (currentUser != null) {
            reportService.warnReport(id, user);
        }else{
            return "/users/login";
        }

        return "redirect:/cases";
    }


    // 제보 신고 페이지 띄우기
    @GetMapping(value = "/cases/detail/{id}/warn")
    public String createCaseWarn(Model model, @PathVariable("id") Long id, @CurrentUser User user) {
        Optional<Case> caseEntity = caseService.findCase(id);

        if(caseEntity.isPresent()) {
            model.addAttribute("case", new CaseResponseDto(caseEntity.get(), caseEntity.get().getUser()));
        }

        model.addAttribute("user", user);
        return "/warn/createCaseWarn";
    }

    // 사건 신고 등록
    @PostMapping(value = "/cases/detail/{id}/warn/submit")
    public String reportCaseWarn(@Valid @ModelAttribute WarnDto form, Model model, @PathVariable("id") Long id, @CurrentUser User currentUser) {
        if (currentUser != null) {
            caseService.warnCase(id, currentUser, form);
        }
        log.info("신고하기 버튼 눌림");
        return "redirect:/cases/detail/{id}";
    }

}
