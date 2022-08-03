//package TheEarthGuard.ComeBackHome.controller;
//
//import TheEarthGuard.ComeBackHome.domain.Case;
//import TheEarthGuard.ComeBackHome.domain.Report;
//import TheEarthGuard.ComeBackHome.domain.User;
//import TheEarthGuard.ComeBackHome.dto.CaseResponseDto;
//import TheEarthGuard.ComeBackHome.dto.ReportFormDto;
//import TheEarthGuard.ComeBackHome.dto.ReportPlaceInfoDto;
//import TheEarthGuard.ComeBackHome.dto.ReportRequestDto;
//import TheEarthGuard.ComeBackHome.repository.CaseRepository;
//import TheEarthGuard.ComeBackHome.repository.ReportRepository;
//import TheEarthGuard.ComeBackHome.repository.UserRepository;
//import TheEarthGuard.ComeBackHome.security.CurrentUser;
//import TheEarthGuard.ComeBackHome.service.CaseService;
//import TheEarthGuard.ComeBackHome.service.ReportService;
//
//import java.sql.Timestamp;
//import java.util.List;
//import java.util.Optional;
//
//import TheEarthGuard.ComeBackHome.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//@Controller
//@SessionAttributes({"reportForm"})
//public class ReportController {
//    private final ReportService reportService;
//    private UserService userService;
//    private CaseService caseService;
//
//    @Autowired
//    public ReportController(ReportService reportService, UserService userService, CaseService caseService) {
//
//        this.reportService = reportService;
//        this.userService = userService;
//        this.caseService = caseService;
//
//    }
//
//    //게시판 등록 화면
//    @GetMapping(value = "/reports/new")
//    public String createForm(Model model) {
//        model.addAttribute("reportForm", new ReportFormDto());
//        return "reports/createReportForm";
//    }
//
//    @PostMapping(value="/reports/new")
//    public String updateReportForm(@ModelAttribute ReportPlaceInfoDto reportPlaceInfoDto, @ModelAttribute("reportForm") ReportFormDto reportForm, HttpServletRequest request, Model model){
//        System.out.println(reportPlaceInfoDto.getWitness_area());
//        System.out.println(reportPlaceInfoDto.getWitness_lat());
//
//        reportForm.setWitness_area(reportPlaceInfoDto.getWitness_area());
//        reportForm.setWitness_lat(reportPlaceInfoDto.getWitness_lat());
//        reportForm.setWitness_lng(reportPlaceInfoDto.getWitness_lng());
//
//        System.out.println("getWitness_lat : " + reportForm.getWitness_lat());
//        System.out.println("title : " + reportForm.getWitness_title());
//
//        model.addAttribute("reportForm", reportForm);
//        return "reports/createReportForm";
//    }
//
//    //게시판 등록
////    @PostMapping(value = "/reports/new/submit")
////    public String createReport(@Valid @ModelAttribute ReportFormDto form, Errors errors){
////        if (errors.hasErrors()) {
////            System.out.println("ERROR!!!!!!!!");
////            return "/";
////        }
////
////        User user = userService.findByEmail("test@gmail.com");
////        System.out.println(user.getEmail());
////
////        Case caseObj= caseService.findCases().get(1);
////        System.out.println(caseObj.getMissing_name());
////
////        System.out.println("title: " + form.getWitness_title());
////        System.out.println("witness_pic: " + form.getWitness_pic());
////
////        Report reportObj = Report.builder()
////                .user(user)
////                .cases(caseObj)
////                .witness_pic(form.getWitness_pic())
////                .witness_title(form.getWitness_title())
////                .witness_desc(form.getWitness_desc())
////                .witness_area(form.getWitness_area())
////                .witness_lat(Double.parseDouble(form.getWitness_lat())) // 계산 필요
////                .witness_lng(Double.parseDouble(form.getWitness_lng()))// 계산 필요
////                .witness_time(Timestamp.valueOf(form.getWitness_time()))
////                .build();
////
////        reportService.UploadReport(reportObj);
////        return "redirect:/";
////
////    }
//
//    @GetMapping(value = "/reports")
//    public String list(Model model) {
//        List<Report> reports = reportService.findReports();
//        model.addAttribute("reports", reports);
//        return "reports/reportList";
//    }
//
//    @GetMapping(value="/reports/new/selectPlace")
//    public String selectPlace(@Valid @ModelAttribute ReportFormDto form, Model model, Errors errors) {
//        if (errors.hasErrors()) {
//            System.out.println("ERROR!!!!!!!!");
//            return "/";
//        }
//        model.addAttribute("reportForm", form);
//        return "/reports/selectPlace";
//    }
//
//    @GetMapping(value = "/reports/new/searchPlace")
//    public String searchPlace() {
//        return "/reports/searchPlace";
//    }
//
//
////    //연동(최신)
////    @GetMapping(value = "/cases/{cases_id}/report")
////    public List<Report> getCaseReports(@PathVariable Long cases_id){
////        Case cases = caseService.findOne(cases_id).get();
////        return
////
////    }
//
//    @PostMapping(value="/cases/{cases_id}/report")
//    public String addReport(@PathVariable("cases_id") Long cases_id, @CurrentUser User user, @ModelAttribute ReportRequestDto reportRequestDto, Model model, Errors errors){
//        if(errors.hasErrors()){
//            System.out.println("Error!!");
//            return "/";
//        }
//
//        String username=user.getUsername();
//
//        Optional<Case> cases = caseService.findOne(cases_id);
//
//        reportRequestDto.setUser(user);
//        reportRequestDto.setCases(cases.get());
//
//        reportService.UploadReport(reportRequestDto);
//
////        List<Report> reports =
//
//        return username;
//
//
//
//
//
//    }
//
//}
