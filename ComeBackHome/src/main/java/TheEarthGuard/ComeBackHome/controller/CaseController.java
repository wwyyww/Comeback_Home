package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.CaseFormDto;
import TheEarthGuard.ComeBackHome.dto.PlaceInfoDto;
import TheEarthGuard.ComeBackHome.dto.SearchFormDto;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.UserService;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"caseForm"})
public class CaseController {
    private final CaseService caseService;
    private UserService userService;

    @Autowired
    public CaseController(CaseService caseService, UserService userService) {
        this.caseService = caseService;
        this.userService = userService;
    }

    @GetMapping(value = "/cases/new")
    public String createCaseForm(Model model) {
        model.addAttribute("caseForm", new CaseFormDto());
        return "cases/createCaseForm";
    }

    @PostMapping(value = "/cases/new")
    public String updateCaseForm(@ModelAttribute PlaceInfoDto placeInfoDto, @ModelAttribute("caseForm") CaseFormDto caseForm,HttpServletRequest request, Model model) {
        // 위의 @ModelAttribute("caseForm"), SessionAttributes  코드로 자동으로 세션으로 객체를 저장해줌
        // 세션 가져와서 placeInfoDto 정보 추가 후, model과 session에 저장
        caseForm.setMissing_area(placeInfoDto.getMissing_area());
        caseForm.setMissing_lat(placeInfoDto.getMissing_lat());
        caseForm.setMissing_lng(placeInfoDto.getMissing_lng());

//        System.out.println(placeInfoDto.getMissing_area());
//        System.out.println(placeInfoDto.getMissing_lat());
//        System.out.println("getMissing_lat : " + caseForm.getMissing_lat());
//        System.out.println("name : " + caseForm.getMissing_name());
//        System.out.println("sex : " + caseForm.getMissing_sex());

        model.addAttribute("caseForm", caseForm);
        return "cases/createCaseForm";
    }

    @PostMapping(value = "/cases/new/submit")
    public String uploadCaseForm(@Valid @ModelAttribute CaseFormDto form, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println("ERROR!!!!!!!!");
            return "/";
        }

        User user = userService.findByEmail("hmk9667@gmail.com");

//        System.out.println(user.getEmail());
//        System.out.println("name : " + form.getMissing_name());
//        System.out.println("missing_pic : " + form.getMissing_pic());
//        System.out.println("missing_sex : " + form.getMissing_sex());
        
        Case caseObj = Case.builder()
            .user(user)
            .missing_pic(form.getMissing_pic())
            .missing_name(form.getMissing_name())
            .missing_age(form.getMissing_age())
            .missing_sex(form.getMissing_sex())
            .missing_desc(form.getMissing_desc())
            .missing_area(form.getMissing_area())
            .missing_lat(Double.parseDouble(form.getMissing_lat()))
            .missing_lng(Double.parseDouble(form.getMissing_lng()))
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


    @PostMapping(value = "/cases/new/selectPlace")
    public String selectPlace(@Valid @ModelAttribute CaseFormDto form, Model model, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println("ERROR!!!!!!!!");
            return "/";
        }

        model.addAttribute("caseForm", form); // 세션으로 같이 등록됨
        return "/cases/selectPlace";
    }


    @PostMapping(value = "/cases/new/searchPlace")
    public String searchPlace(@Valid @ModelAttribute CaseFormDto form, Model model, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println("ERROR!!!!!!!!");
            return "/";
        }

        model.addAttribute("caseForm", form); // 세션으로 같이 등록됨
        return "/cases/searchPlace";
    }


    @GetMapping(value = "/cases/searchCase")
    public String searchCaseForm(SearchFormDto form) {
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
