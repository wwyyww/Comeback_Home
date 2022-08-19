package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.domain.Warn;
import TheEarthGuard.ComeBackHome.repository.CaseRepository;
import TheEarthGuard.ComeBackHome.repository.WarnRepository;
import TheEarthGuard.ComeBackHome.security.CurrentUser;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.ReportService;
import TheEarthGuard.ComeBackHome.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Slf4j
@Controller
public class AdminController  {


    private final UserService userService;
    private final CaseService caseService;
    private final ReportService reportService;

    private final WarnRepository warnRepository;
    private final CaseRepository caseRepository;


    private Map<String, String> warnReasonList=new HashMap<>();


    public AdminController(UserService userService, CaseService caseService, ReportService reportService, WarnRepository warnRepository, CaseRepository caseRepository) {
        this.userService = userService;
        this.caseService = caseService;
        this.reportService = reportService;
        this.warnRepository = warnRepository;
        this.caseRepository = caseRepository;
    }


    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<User> userList = userService.getUserList();
        model.addAttribute("userList", userList);

        return "/admin/userList";
    }

    @GetMapping("/admin/userList")
    public String adminUserList(Model model) {
        List<User> userList = userService.getUserList();
        model.addAttribute("userList", userList);

        return "/admin/userList";
    }

    @GetMapping("/admin/caseList")
    public String adminCaseList(Model model) {
        List<Case> caseList = caseService.getCaseList();
        model.addAttribute("caseList", caseList);

        return "/admin/caseList";
    }

    @GetMapping("/admin/reportList")
    public String adminReportList(Model model) {
        List<Report> reportList = reportService.getReports();
        model.addAttribute("reportList", reportList);

        return "/admin/reportList";
    }

    @GetMapping("/admin/userDetail/{id}")
    public String adminUserDetail(Model model, @PathVariable("id") Long id) {
        Optional<User> user = userService.findById(id);
        model.addAttribute("userDto", user.get());

        return "/admin/userDetail";

    }

    @GetMapping("/admin/reportDetail/{id}")
    public String adminReportDetail(Model model, @PathVariable("id") Long id) {
        Report report = reportService.getReportDetail(id);
        model.addAttribute("report", report);

        return "/admin/reportDetail";

    }

    @GetMapping("/admin/caseDetail/{id}")
    public String adminCaseDetail(Model model, @PathVariable("id") Long id) {
        Optional<Case> caseEntity = caseService.findCase(id);
        model.addAttribute("caseEntity", caseEntity.get());

        return "/admin/caseDetail";

    }

    @GetMapping("/admin/deleteUser/{id}")
    public String adminDeleteUser(@PathVariable("id") Long id, @CurrentUser User user) {
        userService.deleteUser(id, user);

        return "redirect:/admin/userList";

    }

    @GetMapping("/admin/deleteReport/{id}")
    public String adminDeleteReport(@PathVariable("id") Long id, @CurrentUser User user) {
        Report report = reportService.getReportDetail(id);
        reportService.deleteReport(id, report.getUser());

        return "redirect:/admin/reportList";

    }

    @GetMapping("/admin/deleteCase/{id}")
    public String adminDeleteCase(@PathVariable("id") Long id, @CurrentUser User user) {
        Optional<Case> caseEntity = caseService.findCase(id);
        caseService.deleteCase(id, caseEntity.get().getUser());

        return "redirect:/admin/caseList";

    }



    @GetMapping("/admin/reportWarnList/{id}")
    public String reportWarnList(Model model, @PathVariable("id") Long id) {

        Report report = reportService.getReportDetail(id);
        List<Warn> warnList = report.getWarns();
        log.info("warn list : "+warnList);
        model.addAttribute("warnList", warnList);

        return "/admin/warnList";
    }

    @GetMapping("/admin/caseWarnList/{id}")
    public String caseWarnList(Model model, @PathVariable("id") Long id) {

        Optional<Case> caseEntity = caseService.findCase(id);
        List<Warn> warnList = caseEntity.get().getWarns();
        log.info("warn list : "+warnList);
        model.addAttribute("warnList", warnList);

        return "/admin/warnList";
    }




}
