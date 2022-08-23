package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.dto.CaseListResponseDto;
import TheEarthGuard.ComeBackHome.dto.ReportResponseDto;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.ReportService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class MapController {

    private final CaseService caseService;
    private final ReportService reportService;

    @SuppressFBWarnings(justification = "Generated code")
    public MapController(CaseService caseService, ReportService reportService) {
        this.caseService = caseService;
        this.reportService = reportService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/data.json", method = RequestMethod.GET)
    public List<CaseListResponseDto> map(HttpServletRequest req) throws Exception {
        List<Case> caseEntityList = caseService.getCaseList();

        List<CaseListResponseDto> caseDtoList = caseEntityList.stream().map(
            caseEntity -> new CaseListResponseDto(caseEntity, caseEntity.getUser())
        ).collect(Collectors.toList());

        return caseDtoList;
    }

    @RequestMapping(value = "/cases/detailReport/report.json/{id}", method = RequestMethod.GET)
    public List<ReportResponseDto> report(HttpServletRequest request, @PathVariable("id") Long id) throws Exception {
        Optional<Case> findCase = caseService.findCase(id);
        List<ReportResponseDto> reportDtoList = null;
        
        Optional<List<Report>> reportList = Optional.ofNullable(reportService.getReportsListByCase(findCase.get()));
        if (reportList.isPresent()) {
            reportDtoList = reportList.get().stream().map(
                ReportEntity -> new ReportResponseDto(ReportEntity, ReportEntity.getUser())
            ).collect(Collectors.toList());
        }
        return reportDtoList;
    }

}
