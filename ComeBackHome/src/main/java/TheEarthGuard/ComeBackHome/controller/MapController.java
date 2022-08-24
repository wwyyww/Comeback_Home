package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Slf4j
@PropertySource("classpath:application-key.properties")
@RestController
public class MapController {

    private final CaseService caseService;
    private final ReportService reportService;

    private static String GEOCODE_URL="https://dapi.kakao.com/v2/local/search/address";
    private static String GEOCODE_USER_INFO="1eebe8e73c7a718659f309a662119a35";
    public MapController(CaseService caseService, ReportService reportService) {
        this.caseService = caseService;
        this.reportService = reportService;
    }



    @RequestMapping(value = "/data.json", method = RequestMethod.GET)
    public List<Case> map(HttpServletRequest req) throws Exception {
        return caseService.getCaseList();
    }

    //제보 목록 넘겨줌
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


        String[] keys = counter.keySet().toArray(new String[0]);
        Float[] coords = new Float[2];


        List<Object> results=new ArrayList<>();
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(counter.entrySet());
        entries.sort((v1, v2) -> v2.getValue().compareTo(v1.getValue()));
        results.addAll(entries);

        List<Object> resultTest=new ArrayList<>();


        for (Map.Entry<String, Integer> map:entries) {

            Map response = callApi(map.getKey());
            ArrayList documents = (ArrayList) response.get("documents");
            Map addr = (Map) documents.get(0);

            Map<String, Object> objectEntry = new HashMap<String, Object>();

            List list = new ArrayList<>();
            list.add(map.getValue());
            list.add(addr.get("y"));
            list.add(addr.get("x"));

            objectEntry.put(map.getKey(), list);
            resultTest.add(objectEntry);

        }






        return resultTest;
    }

    public Map callApi(String query) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK " + GEOCODE_USER_INFO); //Authorization 설정
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders); //엔티티로 만들기
        URI targetUrl = UriComponentsBuilder
                .fromUriString(GEOCODE_URL) //기본 url
                .queryParam("query", query) //인자
                .build()
                .encode(StandardCharsets.UTF_8) //인코딩
                .toUri();

        //GetForObject는 헤더를 정의할 수 없음
        ResponseEntity<Map> result = restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, Map.class);
        return result.getBody(); //내용 반환
    }




}
