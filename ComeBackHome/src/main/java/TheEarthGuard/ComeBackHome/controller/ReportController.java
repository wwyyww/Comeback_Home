package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.ReportPlaceInfoDto;
import TheEarthGuard.ComeBackHome.dto.ReportRequestDto;
import TheEarthGuard.ComeBackHome.dto.ReportResponseDto;
import TheEarthGuard.ComeBackHome.security.CurrentUser;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.ReportService;

import java.util.List;
import java.util.Optional;

import TheEarthGuard.ComeBackHome.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@Controller
@SessionAttributes({"reportForm"})
public class ReportController {
    private final ReportService reportService;
    private UserService userService;
    private CaseService caseService;

    @Autowired
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

    //실종위치 찍고나서 제보글 이어서 작성할 때
    @PostMapping(value="/reports/new/{id}")
    public String createFormPlace(@ModelAttribute ReportPlaceInfoDto reportPlaceInfoDto, @ModelAttribute("reportForm") ReportRequestDto reportForm,
                                  @PathVariable("id") Long id, Model model){
        reportForm.setWitness_area(reportPlaceInfoDto.getWitness_area());
        reportForm.setWitness_lat(reportPlaceInfoDto.getWitness_lat());
        reportForm.setWitness_lng(reportPlaceInfoDto.getWitness_lng());
        Optional<Case> caseDto = caseService.findCase(id);
        reportForm.setCases(caseDto.get());

        model.addAttribute("reportForm", reportForm);
        return "reports/createReportForm";
    }


    //제보 제출
    @PostMapping(value = "/reports/new/{id}/submit")
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

    @GetMapping(value = "/mypage/reports")
    public String reportList(Model model, @CurrentUser User user) {
        List<Report> reports = reportService.getReportsListByUser(user);
        model.addAttribute("reports", reports);
        return "reports/reportList";
    }

    //제보 상세보기
    @GetMapping(value = "/reports/detail/{id}")
    public String reportDetail(Model model, @PathVariable("id") Long id, @CurrentUser User user) {
        Report reportDto = reportService.getReportDetail(id);

        ReportResponseDto responseDto = new ReportResponseDto(reportDto, user);
        model.addAttribute("report", responseDto);
        model.addAttribute("user", user);
        return "reports/reportDetail";
    }
    
    //제보 삭제하기
    @GetMapping(value = "/reports/delete/{id}")
    public String deleteReport(@PathVariable("id") Long id, @CurrentUser User user) {
        Report report = reportService.getReportDetail(id);
        if (user.getId() == report.getUser().getId()) {
            reportService.deleteReport(id, user);
            return "redirect:/cases";
        }
        return "redirect:/cases";
    }

    //제보 수정하기
    @GetMapping(value = "/reports/update/{id}")
    public String updateReportForm(Model model, @PathVariable("id") Long id, @CurrentUser User user) {
        Report report = reportService.getReportDetail(id);
        ReportResponseDto responseDto = new ReportResponseDto(report, user);
        if (user.getId() == report.getUser().getId()) {
            model.addAttribute("reportForm", responseDto);
            return "/reports/reportUpdate";
        }

        return "redirect:/cases";
    }

    @PostMapping(value = "/reports/update/{id}")
    public String updateReport(@Valid @ModelAttribute ReportRequestDto form, @PathVariable("id") Long id,
                               @CurrentUser User user, Errors errors) throws IllegalAccessException {
        if (errors.hasErrors()) {
            log.info("error!!");
            return "redirect:/cases";
        }
        Report report = reportService.getReportDetail(id);
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




    //지도로 목격위치 찍는 부분
    @PostMapping(value="/reports/new/{id}/searchPlace")
    public String searchPlace(@ModelAttribute ReportRequestDto form, @RequestParam("witnessPics") MultipartFile file, Model model, @PathVariable("id") Long id, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println("ERROR!!!!!!!!");
            return "/reports/createReportForm";
        }
        Optional<Case> caseDto = caseService.findCase(id);
        form.setCases(caseDto.get());
        log.info("searchplace : "+ caseDto.get().getCaseId());
        model.addAttribute("reportForm", form);
        return "/reports/searchPlace";
    }


}
