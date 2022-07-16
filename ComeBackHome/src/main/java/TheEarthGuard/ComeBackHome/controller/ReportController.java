package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.service.ReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "/reports/new")
    public String createForm() {
        return "reports/createReportForm";
    }

    @GetMapping(value = "/reports")
    public String list(Model model) {
        List<Report> reports = reportService.findReports();
        model.addAttribute("reports", reports);
        return "reports/reportList";
    }
}
