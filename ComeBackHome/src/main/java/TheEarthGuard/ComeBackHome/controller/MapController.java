package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.dto.CaseListResponseDto;
import TheEarthGuard.ComeBackHome.dto.ReportResponseDto;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.ReportService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    //제보 목록 넘겨줌
    @RequestMapping(value = "/cases/detailReport/report.json/{id}", method = RequestMethod.GET)
    public List<ReportResponseDto> report(HttpServletRequest request, @PathVariable("id") Long id) throws Exception {
        Optional<Case> findCase = caseService.findCase(id);
        List<ReportResponseDto> reportDtoList = null;

        Optional<List<Report>> reportList = Optional.ofNullable(reportService.getReportsListByCase(findCase.get()));
        if (reportList.isPresent()) {
            reportDtoList = reportList.get().stream().map(
                ReportEntity -> new ReportResponseDto(ReportEntity, ReportEntity.getUser())
            ).collect(Collectors.toList());

            Collections.sort(reportDtoList, new Comparator<ReportResponseDto>() {
                public int compare(ReportResponseDto o1, ReportResponseDto o2) {
                    if (o1.getWitnessTime() == null || o2.getWitnessTime() == null)
                        return 0;
                    return o1.getWitnessTime().compareTo(o2.getWitnessTime());
                }
            });
        }
        return reportDtoList;
    }

    @RequestMapping(value = "/cases/detailReport/area.json/{id}", method = RequestMethod.GET)
    public List<Object> reportPlace(HttpServletRequest req, @PathVariable("id") Long id) throws Exception {
        Map<String, Integer> counter = new HashMap<String, Integer>();


        Optional<Case> findCase = caseService.findCase(id);
        List<Report> reports = reportService.getReportsListByCase(findCase.get());

        //주소 빈도 측정
        for (Report report : reports) {
            String[] areaSplit = report.getWitness_area().split(" ");
            ArrayList<String> areaArray = new ArrayList<>(Arrays.asList(areaSplit));
            areaArray.remove(areaArray.size() - 1);
            String area = String.join(" ", areaArray);
            counter.merge(area, 1, (oldValue, one) -> oldValue + one);
        }


        List<Object> results=new ArrayList<>();
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(counter.entrySet());
        entries.sort((v1, v2) -> v2.getValue().compareTo(v1.getValue()));
        results.addAll(entries);


        return results;
    }

//    @RequestMapping(value = "/cases/detailReport/area.json/{id}", method = RequestMethod.GET)
//    public List<Map.Entry<String, Integer>> reportPlace(HttpServletRequest req, @PathVariable("id") Long id) throws Exception {
//        Map<String, Integer> counter = new HashMap<String, Integer>();
//
//
//        Optional<Case> findCase = caseService.findCase(id);
//        List<Report> reports = reportService.getReportsListByCase(findCase.get());
//
//        for (Report report : reports) {
//            String[] areaSplit = report.getWitness_area().split(" ");
//            ArrayList<String> areaArray = new ArrayList<>(Arrays.asList(areaSplit));
//            areaArray.remove(areaArray.size() - 1);
//            String area = String.join(" ", areaArray);
//            counter.merge(area, 1, (oldValue, one) -> oldValue + one);
//        }
//
//        List<Map.Entry<String, Integer>> entries = new ArrayList<>(counter.entrySet());
//
//        entries.sort((v1, v2) -> v2.getValue().compareTo(v1.getValue()));
//
//        return entries;
//    }

}
