package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.service.CaseService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
