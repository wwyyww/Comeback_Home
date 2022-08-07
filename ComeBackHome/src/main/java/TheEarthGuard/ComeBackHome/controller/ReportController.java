package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.ReportFormDto;
import TheEarthGuard.ComeBackHome.dto.ReportPlaceInfoDto;
import TheEarthGuard.ComeBackHome.dto.ReportRequestDto;
import TheEarthGuard.ComeBackHome.security.CurrentUser;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.ReportService;

import java.util.List;

import TheEarthGuard.ComeBackHome.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @GetMapping(value = "/reports/new")
    public String createForm(Model model) {
        model.addAttribute("reportForm", new ReportFormDto());
        return "reports/createReportForm";
    }

    //실종위치 찍고나서 제보글 이어서 작성할 때
    @PostMapping(value="/reports/new")
    public String updateReportForm(@ModelAttribute ReportPlaceInfoDto reportPlaceInfoDto, @ModelAttribute("reportForm") ReportFormDto reportForm, HttpServletRequest request, Model model){

        reportForm.setWitness_area(reportPlaceInfoDto.getWitness_area());
        reportForm.setWitness_lat(reportPlaceInfoDto.getWitness_lat());
        reportForm.setWitness_lng(reportPlaceInfoDto.getWitness_lng());

        model.addAttribute("reportForm", reportForm);
        return "reports/createReportForm";
    }


    //제보 제출
    @PostMapping(value = "/reports/new/submit")
    public String createReport(@Valid @ModelAttribute ReportRequestDto form, @CurrentUser User user,Errors errors){
        if (errors.hasErrors()) {
            System.out.println("ERROR!!!!!!!!");
            return "/";
        }

        User currentUser = userService.findByEmail(user.getEmail());
//        Case caseObj= caseService.findCases().get(1);
        if (currentUser != null) {
            reportService.uploadReport(currentUser.getId(), 1L, form);
        }else{
            return "/users/login";
        }

        return "redirect:/";

    }

    @GetMapping(value = "/reports")
    public String reportList(Model model, @CurrentUser User user) {
        List<Report> reports = reportService.getReportsListByUser(user);
        model.addAttribute("reports", reports);
        return "reports/reportList";
    }

    //제보 상세보기
    @GetMapping(value = "/reports/detail/{id}")
    public String reportDetail(Model model, @PathVariable("id") Long id, @CurrentUser User user) {
        model.addAttribute("report", reportService.getReportDetail(id));
        model.addAttribute("user", user);
        return "reports/reportDetail";
    }
    
    //제보 삭제하기
    @GetMapping(value = "/reports/delete/{id}")
    public String deleteReport(@PathVariable("id") Long id, @CurrentUser User user) {
        reportService.deleteReport(id, user);
        return "redirect:/reports";
    }

    //제보 수정하기
    @GetMapping(value = "/reports/update/{id}")
    public String updateReport(@PathVariable("id") Long id, @CurrentUser User user) {
        return "redirect:/reports";
    }


    //지도로 목격위치 찍는 부분
    @PostMapping(value="/reports/new/searchPlace")
    public String searchPlace(@Valid @ModelAttribute ReportFormDto form, Model model, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println("ERROR!!!!!!!!");
            return "/reports/createReportForm";
        }
        model.addAttribute("reportForm", form);
        return "/reports/searchPlace";
    }


//    //연동(최신)
//    @GetMapping(value = "/cases/{cases_id}/report")
//    public List<Report> getCaseReports(@PathVariable Long cases_id){
//        Case cases = caseService.findOne(cases_id).get();
//        return
//
//    }

    @PostMapping(value="/cases/{cases_id}/report")
    public String addReport(@PathVariable("cases_id") Long cases_id, @CurrentUser User user, @ModelAttribute ReportRequestDto reportRequestDto, Model model, Errors errors){
        if(errors.hasErrors()){
            System.out.println("Error!!");
            return "/";
        }

//        String username=user.getUsername();
//        Optional<Case> cases = caseService.findOne(cases_id);
//        reportRequestDto.setUser(user);
//        reportRequestDto.setCases(cases.get());

        reportService.uploadReport(user.getId(), 1L, reportRequestDto);
//        reportService.uploadReport(user.getId(), case_id, reportRequestDto);


        return "reports/createReportForm";

    }

}
