package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.service.CaseService;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CaseController {
    private final CaseService caseService;

    @Autowired
    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @GetMapping(value = "/cases/new")
    public String createForm() {
        return "cases/createCaseForm";
    }

    @PostMapping(value = "/cases/new")
    public String create(CaseForm form) {
        Case caseObj = new Case();
        // default 값 설정
        caseObj.setIs_find(false);
        caseObj.setReport_cnt(0);
        caseObj.setHit_cnt(0);

        // 사용자 id 설정
        caseObj.setFinder_id("finderId123");

        // form 정보 반영
        caseObj.setMissing_name(form.getMissing_name());
        caseObj.setMissing_pic(form.getMissing_pic());
        caseObj.setMissing_age(form.getMissing_age());
        caseObj.setMissing_sex(form.getMissing_sex());
        caseObj.setMissing_desc(form.getMissing_desc());
        caseObj.setMissing_area(form.getMissing_area());
        caseObj.setMissing_lat(26.41315621); // 계산 필요
        caseObj.setMissing_lng(18.456132121); // 계산 필요
        caseObj.setMissing_time(form.getMissing_time());

//        Timestamp missing_time3 = Timestamp.valueOf("1880-12-12 01:24:23");

        System.out.println("[CASE_CONTROLLER] caseObj.getMissing_time" + caseObj.getMissing_time());

        caseService.UploadCase(caseObj);
        return "redirect:/";
    }

    @GetMapping(value = "/cases")
    public String list(Model model) {
        List<Case> cases = caseService.findCases();
        model.addAttribute("cases", cases);
        return "cases/caseList";
    }

//    @GetMapping(value = "/cases/search")
//    public String research(String keyword, Model model) {
//        List<Case> searchlist = caseService.search(keyword);
//        model.addAttribute("searchlist", searchlist);
//        return "cases/search-result";
//    }
}
