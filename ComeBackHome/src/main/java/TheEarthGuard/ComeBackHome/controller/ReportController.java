package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.service.ReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    //게시판 등록 화면
    @GetMapping(value = "/reports/new")
    public String createForm() {

        return "reports/createReportForm";
    }

    //게시판 등록
    @PostMapping(value = "/reports/new")
    public String createReport(ReportForm form){

        Report reportObj = new Report();

        reportObj.setIs_alert(false);
        reportObj.setCase_id("1"); //하드코딩
        reportObj.setUser_id("aa"); //하드코딩

        reportObj.setWitness_area(form.getWitness_area());
        reportObj.setWitness_time(form.getWitness_time());
        reportObj.setWitness_txt(form.getWitness_txt());
//        reportObj.setWitness_pic(form.getWitness_pic());

        reportService.UploadReport(reportObj);
        return "redirect:/";
    }

    @GetMapping(value = "/reports")
    public String list(Model model) {
        List<Report> reports = reportService.findReports();
        model.addAttribute("reports", reports);
        return "reports/reportList";
    }
}
