package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;


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

    //제보 목록 넘겨줌
    @RequestMapping(value = "/cases/detailReport/report.json/{id}", method = RequestMethod.GET)
    public List<Report> report(HttpServletRequest request, @PathVariable("id") Long id) throws Exception {
        Optional<Case> findCase = caseService.findCase(id);
        List<Report> reportList = reportService.getReportsListByCase(findCase.get());
        Collections.sort(reportList, new Comparator<Report>() {
            public int compare(Report o1, Report o2) {
                if (o1.getWitnessTime() == null || o2.getWitnessTime() == null)
                    return 0;
                return o1.getWitnessTime().compareTo(o2.getWitnessTime());
            }
        });

        return reportList;
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
