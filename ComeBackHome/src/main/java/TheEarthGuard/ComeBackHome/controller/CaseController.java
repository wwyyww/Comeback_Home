package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.CaseResponseDto;
import TheEarthGuard.ComeBackHome.dto.CaseSaveRequestDto;
import TheEarthGuard.ComeBackHome.dto.PlaceInfoDto;
import TheEarthGuard.ComeBackHome.dto.SearchFormDto;
import TheEarthGuard.ComeBackHome.security.CurrentUser;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.FileHandler;
import TheEarthGuard.ComeBackHome.service.ReportService;
import TheEarthGuard.ComeBackHome.service.UserService;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@SessionAttributes({"caseDto"})
public class CaseController {
    private final CaseService caseService;
    private UserService userService;
    private FileHandler fileHandler;
    private final ReportService reportService;



    public CaseController(CaseService caseService, UserService userService, FileHandler fileHandler, ReportService reportService) {
        this.caseService = caseService;
        this.userService = userService;
        this.fileHandler = fileHandler;
        this.reportService = reportService;
    }

    // 처음 사건 등록할 때
    @GetMapping(value = "/cases/new")
    public String createCaseForm(Model model) {
        model.addAttribute("caseDto",  new CaseSaveRequestDto());
        return "cases/createCaseForm";
    }

    //실종위치 찍고나서 사건글 이어서 작성할 때
    @PostMapping(value = "/cases/new")
    public String updateCaseForm(@ModelAttribute PlaceInfoDto placeInfoDto, @ModelAttribute("caseDto") CaseSaveRequestDto caseDto,HttpServletRequest request, Model model) {
        caseDto.setMissingArea(placeInfoDto.getMissingArea());
        caseDto.setMissingLat(placeInfoDto.getMissingLat());
        caseDto.setMissingLng(placeInfoDto.getMissingLng());

        model.addAttribute("caseDto", caseDto);
        return "cases/createCaseForm";
    }

    // 사건 등록
    @PostMapping(value = "/cases/new/submit")
    public String uploadCaseForm(@Valid @ModelAttribute CaseSaveRequestDto caseDto, @CurrentUser User user, Errors errors, Model model) throws Exception {
        if (errors.hasErrors()) {
            log.info("error!!" + errors);
            model.addAttribute("caseDto", caseDto);

            Map<String, String> validatorResult = caseService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "cases/createCaseForm";
        }
        User currentUser = userService.findByEmail(user.getEmail());
        caseDto.setUser(currentUser);
        caseService.UploadCase(caseDto, caseDto.getMissingPics());

        return "redirect:/";
    }

    // 장소 검색
    @PostMapping(value = "/cases/new/searchPlace")
    public String searchPlace(@ModelAttribute CaseSaveRequestDto caseDto, @RequestParam("missingPics") MultipartFile file, Model model, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println("ERROR!!!!!!!!");
            // 에러 페이지 수정 필요
            return "cases/createCaseForm";
        }
        model.addAttribute("caseDto", caseDto);// 세션으로 같이 등록됨
        return "/cases/searchPlace";
    }

    // 모든 사건 조회
    @GetMapping(value = "/cases")
    public String caseList(Model model) {
        List<Case> cases = caseService.getCaseList();
        model.addAttribute("cases", cases);
        return "cases/caseList";
    }

    // 로그인 한 사용자의 사건 리스트로 조회
    @GetMapping(value = "/mypage/cases")
    public String caseListByUser(Model model, @CurrentUser User user) {
        Optional<List<Case>> caseList = caseService.findCaseByUser(user);
        if(caseList.isPresent()){
            model.addAttribute("cases", caseList);
        }
        return "cases/caseList";
    }

    // 사건 상세보기
    @GetMapping(value = "/cases/detail/{id}")
    public String caseDetail(Model model, @PathVariable("id") Long id, @CurrentUser User user) {
        Optional<Case> caseDto = caseService.findCase(id);
        if(caseDto.isPresent()) {
            model.addAttribute("case", new CaseResponseDto(caseDto.get(), caseDto.get().getUser()));
            model.addAttribute("missing_pics", caseDto.get().getMissingPics());
        }
        model.addAttribute("user", user);
        if (user.getId() == caseDto.get().getUser().getId()) {
            List<Report> reports = reportService.getReportsListByCase(caseDto.get());
            model.addAttribute("reports", reports);
            return "/cases/caseDetail";
        }
        return "/cases/caseDetail";
    }

    // 사건 삭제하기
    @GetMapping(value = "/cases/delete/{id}")
    public String deleteCase(@PathVariable("id") Long id, @CurrentUser User user) {
        caseService.deleteCase(id, user);
        return "redirect:/cases";
    }

    // 사진 출력
    @ResponseBody
    @GetMapping("/images/{filepath}/{filename}")
    public UrlResource processImg(@PathVariable String filepath, @PathVariable String filename) throws MalformedURLException {//
        return new UrlResource("file:" + fileHandler.createPath(filepath, filename));
    }

    // 사진 출력 (썸네일)
//    @ResponseBody
//    @GetMapping("/images/{filepath}/{filename}")
//    public UrlResource processThumbImg(@PathVariable String filepath, @PathVariable String filename) throws MalformedURLException {//
//        return new UrlResource("file:" + fileHandler.createThumbPath(filepath, filename));
////        return URLEncoder.encode("file:" + fileHandler.createThumbPath(filepath, filename),"UTF-8");
//    }


    // 사건 수정하기
    @GetMapping(value = "/cases/update/{id}")
    public String updateCaseForm(Model model, @PathVariable("id") Long caseId) {
        Optional<Case> caseDto = caseService.findCase(caseId);
        if(caseDto.isPresent()){
            model.addAttribute("case", new CaseResponseDto(caseDto.get(), caseDto.get().getUser()));
        }
        return "/cases/caseUpdate";
    }

    @PostMapping(value = "/cases/update/{id}")
    public String updateCase(@Valid @ModelAttribute CaseSaveRequestDto caseDto, @PathVariable("id") Long caseId,
        @CurrentUser User user, Errors errors) {
        if (errors.hasErrors()) {
            log.info("error!!");
            return "redirect:/cases";
        }

//        caseService.updateReport(caseDto, caseId, user);
        return "redirect:/reports/detail/{id}";
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
//            caseList = caseService.findbyMissingName(form.getMissing_name(), sex, age, area);
        } else if (form.getSearch_type().equals("area")) {
            System.out.println(form.getMissing_name());
            System.out.println(form.getSearch_type());
//            caseList = caseService.findbyMissingArea(form.getMissing_name());
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
