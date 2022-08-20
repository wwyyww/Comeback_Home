package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
public class MapController {

    private final CaseService caseService;
    private final ReportService reportService;

    public MapController(CaseService caseService, ReportService reportService) {
        this.caseService = caseService;
        this.reportService = reportService;
    }



    @RequestMapping(value = "/data.json", method = RequestMethod.GET)
    public List<Case> map(HttpServletRequest req) throws Exception {
        return caseService.getCaseList();
    }

    @RequestMapping(value = "/cases/detailReport/report.json/{id}", method = RequestMethod.GET)
    public List<Report> report(HttpServletRequest request, @PathVariable("id") Long id) throws Exception {
        log.info("requestmapping : " + request.getRequestURL().toString());
        log.info("id : " + id);

        Optional<Case> findCase = caseService.findCase(id);
        return reportService.getReportsListByCase(findCase.get());
    }

}
