package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.CaseFormDto;
import TheEarthGuard.ComeBackHome.dto.PlaceInfoDto;
import TheEarthGuard.ComeBackHome.dto.SearchFormDto;
import TheEarthGuard.ComeBackHome.service.CaseService;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import TheEarthGuard.ComeBackHome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CaseController {
    private final CaseService caseService;
    private UserService userService;

    @Autowired
    public CaseController(CaseService caseService, UserService userService) {
        this.caseService = caseService;
        this.userService = userService;
    }


    @GetMapping(value = "/cases/new")
    public String createCaseForm() {
        return "cases/createCaseForm";
    }

    @PostMapping(value = "/cases/new")
    public String updateCaseForm(@ModelAttribute PlaceInfoDto placeInfoDto, Model model) {
        System.out.println(placeInfoDto.getMissing_area());
        System.out.println(placeInfoDto.getMissing_lat());
        model.addAttribute("place", placeInfoDto);
        return "cases/createCaseForm";
    }

    @PostMapping(value = "/cases/new/submit")
    public String uploadCaseForm(@ModelAttribute CaseFormDto form) {
        User user = userService.findByEmail("hmk9667@gmail.com");
        System.out.println(user.getEmail());

        System.out.println("name : " + form.getMissing_name());
        System.out.println("missing_pic : " + form.getMissing_pic());
        
        Case caseObj = Case.builder()
            .user(user)
            .missing_pic(form.getMissing_pic())
            .missing_name(form.getMissing_name())
            .missing_age(form.getMissing_age())
            .missing_sex(form.getMissing_sex())
            .missing_desc(form.getMissing_desc())
            .missing_area(form.getMissing_area())
            .missing_lat(Double.parseDouble(form.getMissing_lat())) // 계산 필요
            .missing_lng(Double.parseDouble(form.getMissing_lng())) // 계산 필요
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


//    @RequestMapping(value="/cases/new/missing-place/{option}", method= RequestMethod.GET)
//    public String setMissingPlace(@RequestParam(value="option", required =false, defaultValue="1") String opt) {
//        if (opt.equals("1")) {
//            return "/cases/selectPlace";
//        } else if (opt.equals("2")) {
//            return "/cases/searchPlace";
//        } else {
//            return "/error/";
//        }



    @GetMapping(value = "/cases/new/selectPlace")
    public String selectPlace() {
        return "/cases/selectPlace";
    }

    @GetMapping(value = "/cases/new/searchPlace")
    public String searchPlace() {
        return "/cases/searchPlace";
    }



    @GetMapping(value = "/cases/searchCase")
    public String searchCaseForm() {
        return "/cases/searchCaseForm";
    }

//    @PostMapping(value = "/cases/searchCase")
//    public String searchCaseForm(Model model) {
//        model.addAttribute("place", placeInfoDto);
//        return "cases/searchCaseForm";
//    }

    @PostMapping(value = "/cases/search/submit")
    public String showCaseForm(SearchFormDto form, Model model) {
        System.out.println(form.getMissing_name());
        Optional<Case> searchList = caseService.findOnebyMissingName(form.getMissing_name());
        if(searchList.isPresent()) {
            System.out.println(searchList.get());
            System.out.println(searchList.get().getMissing_name());
        } else {
            System.out.println("없음");
        }
        model.addAttribute("searchList", searchList.get());
        return "/cases/searchCaseForm";
    }

}
