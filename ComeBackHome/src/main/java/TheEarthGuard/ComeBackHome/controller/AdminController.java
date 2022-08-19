package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.domain.Warn;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
public class AdminController  {


    private final UserService userService;
    private final CaseService caseService;
    private final ReportService reportService;

    private final WarnRepository warnRepository;

    private Map<String, String> warnReasonList;


    public AdminController(UserService userService, CaseService caseService, ReportService reportService, WarnRepository warnRepository) {
        this.userService = userService;
        this.caseService = caseService;
        this.reportService = reportService;
        this.warnRepository = warnRepository;
        warnReasonList.put("1", "허위신고");
        warnReasonList.put("2", "욕설/비하");
        warnReasonList.put("3", "낚시/놀람/도배");
        warnReasonList.put("4", "유출/사칭/사기");
        warnReasonList.put("5", "상업적 광고 및 판매");
        warnReasonList.put("6", "희롱 또는 괴롭힘");
        warnReasonList.put("7", "음란물/불건전한 만남 및 대화");
        warnReasonList.put("8", "정당/정치인 비하 및 선거운동");
    }


    @GetMapping("/admin")
    public String adminPage() {

        return "/admin/admin_main";
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

    @GetMapping("/admin/deleteUser/{id}")
    public String adminDeleteUser(@PathVariable("id") Long id, @CurrentUser User user) {
        userService.deleteUser(id, user);

        return "redirect:/admin/userList";

    }

    @GetMapping("/admin/warnList")
    public String adminWarnList(Model model) {



        List<Warn> warnList = warnRepository.findAll();


        model.addAttribute("warnList", warnList);



        return "/admin/warnList";
    }




}
