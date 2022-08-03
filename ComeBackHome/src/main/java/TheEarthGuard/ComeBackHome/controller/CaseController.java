package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.CaseRequestDto;
import TheEarthGuard.ComeBackHome.dto.PlaceInfoDto;
import TheEarthGuard.ComeBackHome.dto.SearchFormDto;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.UserService;
import java.io.File;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SessionAttributes({"caseDto"})
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
//        model.addAttribute("caseForm", new CaseRequestDto());
        model.addAttribute("caseDto",  new CaseRequestDto());
        return "cases/createCaseForm";
    }

    @PostMapping(value = "/cases/new")
    public String updateCaseForm(@ModelAttribute PlaceInfoDto placeInfoDto, @ModelAttribute("caseDto") CaseRequestDto caseDto,HttpServletRequest request, Model model) {
        // 위의 @ModelAttribute("caseForm"), SessionAttributes  코드로 자동으로 세션으로 객체를 저장해줌
        // 세션 가져와서 placeInfoDto 정보 추가 후, model과 session에 저장
        caseDto.setMissing_area(placeInfoDto.getMissing_area());
        caseDto.setMissing_lat(placeInfoDto.getMissing_lat());
        caseDto.setMissing_lng(placeInfoDto.getMissing_lng());

        model.addAttribute("caseDto", caseDto);
        return "cases/createCaseForm";
    }

    @PostMapping(value = "/cases/new/submit")
    public String uploadCaseForm(@Valid CaseRequestDto caseDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            System.out.println("ERROR!!!!!!!!");
            model.addAttribute("caseDto", caseDto);

            Map<String, String> validatorResult = caseService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "cases/createCaseForm";
        }

            User user = userService.findByEmail("test@gmail.com");
            System.out.println(user.getEmail());

            caseService.UploadCase(caseDto, user);
            return "redirect:/";
    }

    @GetMapping(value = "/cases")
    public String list(Model model) {
        List<Case> cases = caseService.findCases();
        model.addAttribute("cases", cases);
        return "cases/caseList";
    }

    @PostMapping(value = "/cases/new/searchPlace")
    public String searchPlace(@ModelAttribute CaseRequestDto caseDto, @RequestParam("missing_pic") MultipartFile file, Model model, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println("ERROR!!!!!!!!");
            // 에러 페이지 수정 필요
            return "cases/createCaseForm";
        }

        // 임시 파일 저장
        //String uploadFolder= "D:\\ComeBackHome\\tmpImg";
        File uploadFolder = new File("D:\\ComeBackHome\\tmpImg");
        if (! uploadFolder.exists()){
            uploadFolder.mkdirs();
        }
        System.out.println("file 명 : " + file.getOriginalFilename());
        System.out.println("file 사이즈 : " + file.getSize());

        File saveFile = new File(uploadFolder, file.getOriginalFilename());

        try{
            file.transferTo(saveFile);
        }catch (Exception e){

        }

        model.addAttribute("caseDto", caseDto);// 세션으로 같이 등록됨
        return "/cases/searchPlace";
    }


    @GetMapping(value = "/cases/searchCase")
    public String searchCaseForm(SearchFormDto form) {
        return "/cases/searchCaseForm";
    }


    @PostMapping(value = "/cases/search/submit")
    public String showCaseForm(SearchFormDto form, Model model) {
        Optional<List<Case>> caseList = Optional.empty();
        Optional<List<String>> sex = form.getMissing_sex();
        Optional<List<String>> age = form.getMissing_age();
        Optional<List<String>> area = form.getMissing_area();
        System.out.println(sex);
        System.out.println(age);
        System.out.println(area);
        if (form.getSearch_type().equals("name")){
            System.out.println(form.getMissing_name());
            System.out.println(form.getSearch_type());
            caseList = caseService.findbyMissingName(form.getMissing_name(), sex, age, area);
        } else if (form.getSearch_type().equals("area")) {
            System.out.println(form.getMissing_name());
            System.out.println(form.getSearch_type());
            caseList = caseService.findbyMissingArea(form.getMissing_name());
        } else {

        }
        //Optional<Case> searchList = caseService.findOnebyMissingName(form.getMissing_name());
        if(caseList.isPresent()) {
            System.out.println(caseList.get());
            //System.out.println(caseList.get().getMissing_name());
            model.addAttribute("searchList", caseList.get());
        } else {
            System.out.println("없음");
        }
        // "redirect:/cases/searchCase";
        return "/cases/searchCaseForm";
    }



}
