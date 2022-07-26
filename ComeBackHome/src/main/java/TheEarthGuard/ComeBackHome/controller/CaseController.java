package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.dto.CaseFormDto;
import TheEarthGuard.ComeBackHome.dto.PlaceInfoDto;
import TheEarthGuard.ComeBackHome.service.CaseService;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CaseController {
    private final CaseService caseService;

    @Autowired
    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @GetMapping(value = "/cases/new")
    public String createCaseForm() {
        return "cases/createCaseForm";
    }

    @PostMapping(value = "/cases/new")
    public String updateCaseForm(@RequestBody PlaceInfoDto placeInfoDto, Model model) {
        System.out.println(placeInfoDto);
        model.addAttribute("place", placeInfoDto);
        return "cases/createCaseForm";
    }

    @PostMapping(value = "/cases/new/submit")
    public String uploadCaseForm(CaseFormDto form) {
        Case caseObj = Case.builder()
            .finder_id("finderId123")
            .missing_pic(form.getMissing_pic())
            .missing_name(form.getMissing_name())
            .missing_age(form.getMissing_age())
            .missing_sex(form.getMissing_sex())
            .missing_desc(form.getMissing_desc())
            .missing_area(form.getMissing_area())
            .missing_lat(26.41315621) // 계산 필요
            .missing_lng(18.456132121) // 계산 필요
            .missing_time(Timestamp.valueOf(form.getMissing_time()))
            .build();

        caseService.UploadCase(caseObj);
        return "redirect:/";
    }

    @GetMapping(value = "/cases")
    public String list(Model model) {
        List<Case> cases = caseService.findCases();
        model.addAttribute("cases", cases);
        return "cases/caseList";
    }

    @GetMapping(value = "/cases/new/selectPlace")
    public String selectPlace() {
        return "/cases/selectPlace";
    }

    @GetMapping(value = "/cases/new/searchPlace")
    public String searchPlace() {
        return "/cases/searchPlace";
    }
}
