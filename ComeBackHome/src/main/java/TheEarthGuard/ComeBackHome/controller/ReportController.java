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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    //게시판 등록 화면
    @GetMapping(value = "/reports/new")
    public String createForm(Model model) {
        model.addAttribute("reportForm", new ReportFormDto());
        return "reports/createReportForm";
    }

    @PostMapping(value="/reports/new")
    public String updateReportForm(@ModelAttribute ReportPlaceInfoDto reportPlaceInfoDto, @ModelAttribute("reportForm") ReportFormDto reportForm, HttpServletRequest request, Model model){

        reportForm.setWitness_area(reportPlaceInfoDto.getWitness_area());
        reportForm.setWitness_lat(reportPlaceInfoDto.getWitness_lat());
        reportForm.setWitness_lng(reportPlaceInfoDto.getWitness_lng());

        model.addAttribute("reportForm", reportForm);
        return "reports/createReportForm";
    }


    //제보 등록
    @PostMapping(value = "/reports/new/submit")
    public String createReport(@Valid @ModelAttribute ReportRequestDto form, Errors errors){
        if (errors.hasErrors()) {
            System.out.println("ERROR!!!!!!!!");
            return "/";
        }

        User user = userService.findByEmail("test@gmail.com");
//        Case caseObj= caseService.findCases().get(1);

        reportService.UploadReport(user.getId(), 1L, form);
        return "redirect:/";

    }

    @GetMapping(value = "/reports")
    public String list(Model model) {
        List<Report> reports = reportService.findReports();
        model.addAttribute("reports", reports);
        return "reports/reportList";
    }

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

        reportService.UploadReport(user.getId(), 1L, reportRequestDto);
//        reportService.UploadReport(user.getId(), case_id, reportRequestDto);


        return "reports/createReportForm";

    }

}
