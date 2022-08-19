package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.service.CaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Slf4j
@RestController
public class MapController {

    private final CaseService caseService;

    public MapController(CaseService caseService) {
        this.caseService = caseService;
    }



    @GetMapping("/caseMap")
    public List<Case> map(HttpServletRequest req) throws Exception {
        return caseService.getCaseList();
    }

}
