package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.CaseListResponseDto;
import TheEarthGuard.ComeBackHome.dto.CaseResponseDto;
import TheEarthGuard.ComeBackHome.dto.CaseSaveRequestDto;
import TheEarthGuard.ComeBackHome.dto.Message;
import TheEarthGuard.ComeBackHome.dto.SearchFormDto;
import TheEarthGuard.ComeBackHome.security.CurrentUser;
import TheEarthGuard.ComeBackHome.service.CaseService;
import TheEarthGuard.ComeBackHome.service.ReportService;
import TheEarthGuard.ComeBackHome.service.UserService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

@Slf4j
@CrossOrigin("*")
@Controller
@SessionAttributes({"caseDto", "RedirectURL"})
public class CaseController {
    private final CaseService caseService;
    private final UserService userService;
    private final ReportService reportService;

    @SuppressFBWarnings(justification = "Generated code")
    @Autowired
    public CaseController(CaseService caseService, UserService userService, ReportService reportService) {
        this.caseService = caseService;
        this.userService = userService;
        this.reportService = reportService;
    }

    @GetMapping(value = "/")
    public String caseMap(Model model, @CurrentUser User user) {
        List<Case> caseEntityList = caseService.getCaseList();
        List<CaseListResponseDto> caseDtoList = caseEntityList.stream().map(
                caseEntity -> new CaseListResponseDto(caseEntity, caseEntity.getUser())
        ).collect(Collectors.toList());

        Map<String, Object> returnMap=new HashMap<String, Object>();

        returnMap.put("cases", caseDtoList);

        model.addAttribute("cases", returnMap);

        System.out.println(returnMap);
        var a = caseService.countCase();
        var b = caseService.countCase_find();
        var c = reportService.countReport();
        System.out.println("접수된 사건 수 : " + a);
        System.out.println("해결된 사건 수 : " + b);
        System.out.println("전체 제보 수 : " + c);

        model.addAttribute("count1", a);
        model.addAttribute("count2", b);
        model.addAttribute("count3", c);
//

//        model.addObject("casesList", caseDtoList);

        return "home";
    }

    @GetMapping(value = "/detailMap")
    public String detailMap(Model model) {
        List<Case> caseEntityList = caseService.getCaseList();
        List<CaseListResponseDto> caseDtoList = caseEntityList.stream().map(
                caseEntity -> new CaseListResponseDto(caseEntity, caseEntity.getUser())
        ).collect(Collectors.toList());

        Map<String, Object> returnMap=new HashMap<String, Object>();

        returnMap.put("cases", caseDtoList);

        model.addAttribute("cases", returnMap);

        System.out.println(returnMap);
//        model.addObject("casesList", caseDtoList);
        return "/allmaps/casesMap/reportsMap";
    }

    // 처음 사건 등록할 때
    @GetMapping(value = "/cases/new")
    public ModelAndView createCaseForm(ModelAndView mav, @CurrentUser User user) {
        if (user == null) {
//            return "/users/login";
            mav.setViewName("/users/login");
            return mav;
        }

//        model.addAttribute("caseDto",  new CaseSaveRequestDto());
//        return "cases/createCaseForm";
        mav.addObject("caseDto",  new CaseSaveRequestDto());
        mav.setViewName("cases/createCaseForm");
        return mav;
    }


//    @CrossOrigin("*")
//    @GetMapping(value = "/test")
//    public String Form(Model model) {
//        return "reports/test";
//    }

    // 사건 등록
    @PostMapping(value = "/cases/new")
    ModelAndView uploadCaseForm(@Valid @ModelAttribute CaseSaveRequestDto caseDto, @CurrentUser User user, Errors errors, ModelAndView mav) throws Exception {
        if (errors.hasErrors()) {
            log.info("error!!" + errors);
            mav.addObject("caseDto", caseDto);

            Map<String, String> validatorResult = caseService.validateHandling(errors);
            for (Map.Entry<String,String> entry : validatorResult.entrySet()){
                mav.addObject(entry.getKey(), entry.getValue());
            }

//            return "cases/createCaseForm";ㅅㄷ
//            mav.addObject("data", new Message("모든 필드를 입력해주세요.", "cases/createCaseForm"));
            mav.setViewName("cases/createCaseForm");
            return mav;
        }
        User currentUser = userService.findByEmail(user.getEmail());
        caseDto.setUser(currentUser);
        Long caseId = caseService.uploadCase(caseDto, caseDto.getMissingPics());

//        if(caseId == null)
//        {
//            model.addAttribute("message", "유해 이미지가 포함되어 업로드가 취소되었습니다.");
//            model.addAttribute("searchUrl", "/cases");
//            return "message";
//        }
//        model.addAttribute("message", "사건 업로드 성공");
//        model.addAttribute("searchUrl", "/cases");
//        return "message";

        if(caseId == null){
            mav.addObject("data", new Message("유해 이미지가 포함되어 업로드가 취소되었습니다.", "/"));
            mav.setViewName("Message");
            return mav;
        }

        mav.addObject("data", new Message("실종자 등록이 완료되었습니다.", "/"));
        mav.setViewName("Message");
        return mav;
    }

    // 모든 사건 조회
    @GetMapping(value = "/cases")
    public String caseList(Model model, @RequestParam(value="page", defaultValue="0") int page){
        Page<Case> pagingList = caseService.getCasePList(page);
        Page<CaseListResponseDto> pagingDtoList = new CaseListResponseDto().toDtoList(pagingList);
        model.addAttribute("cases", pagingDtoList);

        int startPage = Math.max(1, pagingDtoList.getPageable().getPageNumber() - 10);
        int endPage = Math.min(pagingDtoList.getTotalPages(), pagingDtoList.getPageable().getPageNumber() + 10);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("cases", pagingDtoList);
        return "cases/caseList";
    }

//        List<Case> caseEntityList = caseService.getCaseList();
//
//        List<CaseListResponseDto> caseDtoList = caseEntityList.stream().map(
//            caseEntity -> new CaseListResponseDto(caseEntity, caseEntity.getUser())
//        ).collect(Collectors.toList());
//
//        model.addAttribute("cases", caseDtoList);
//        return "cases/caseList";
//    }

    // 로그인 한 사용자의 사건 리스트로 조회
    @GetMapping(value = "/mypage/cases")
    public String caseListByUser(Model model, @CurrentUser User user,@PageableDefault(size=12) Pageable pageable,@RequestParam(value="page", defaultValue="0") int page) {
        Optional<List<Case>> caseEntityList = caseService.findCaseByUser(user);

        if (caseEntityList.isPresent()) {
            List<CaseListResponseDto> caseDtoList = caseEntityList.get().stream().map(
                    caseEntity -> new CaseListResponseDto(caseEntity, caseEntity.getUser())
            ).collect(Collectors.toList());
//            model.addAttribute("cases", caseDtoList);

            // 페이징 변환 작업
            final int startPage = (int)pageable.getOffset();
            final int endPage = Math.min((startPage + pageable.getPageSize()), caseDtoList.size());
            Page<CaseListResponseDto> pagingDtoList = new PageImpl<>(caseDtoList.subList(startPage, endPage), pageable, caseDtoList.size());

            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("cases", pagingDtoList);
        }

        return "cases/caseList";
    }

    // 사건 상세보기
    @GetMapping(value = "/cases/detail/{id}")
    public String caseDetail(@ModelAttribute SearchFormDto form, Model model, @PathVariable("id") Long id, @CurrentUser User user) {
        Optional<Case> caseEntity = caseService.findCase(id);

        if(caseEntity.isPresent()) {
            caseService.countHitCase(caseEntity.get().getCaseId()); // hit ++
            model.addAttribute("case", new CaseResponseDto(caseEntity.get(), caseEntity.get().getUser()));
        }
        model.addAttribute("user", user);

        if (caseEntity.isPresent()) {
            if (user != null && (user.getId().equals(caseEntity.get().getUser().getId()))) {
                List<Report> reports = reportService.getReportsListByCase(caseEntity.get());
                model.addAttribute("reports", reports);
            }

        if (user != null) {
            List<Report> reportList = reportService.getReportsListByCase(caseEntity.get());
            List<Report> reports = new ArrayList<>();
            for (Report report : reportList) {
                if (report.getUser().getId().equals(user.getId())) {
                    reports.add(report);
                }
            }
            log.info("reports : " + reports);
            model.addAttribute("reports", reports);
        }
        }
        return "/cases/caseDetail";
    }

    @GetMapping(value = "/cases/detailReport/{id}")
    public String caseDetailMap(@PathVariable("id") Long id, Model model) {
        Optional<Case> caseEntity = caseService.findCase(id);
        caseEntity.ifPresent(aCase -> model.addAttribute("caseEntity", aCase));
        return "/allmaps/casesMap/reportsMap";
    }


    @PostMapping(value = "/cases/detail/{id}/submit")
    public String showCaseDetail(@ModelAttribute SearchFormDto form, Model model, @PathVariable("id") Long id, @CurrentUser User user, RedirectAttributes redirectAttributes) {
       if(user == null){
           return "/";
       }
        Optional<List<Report>> reportList = Optional.empty();
        String area = form.getMissing_area2();
        LocalDate start = form.getMissing_time_start();
        LocalDate end = form.getMissing_time_end();
        System.out.println(id + area + start + end);
        reportList = reportService.getByFilters(area, start, end);
        if(reportList.isPresent()) {
            System.out.println(reportList.get());
        } else {
            System.out.println("없음");
        }

        redirectAttributes.addFlashAttribute("searchReport", reportList);
        //return "/cases/caseDetail";
        return "redirect:/cases/detail/{id}";
    }

    // 사건 삭제하기
    @GetMapping(value = "/cases/delete/{id}")
    public String deleteCase(@PathVariable("id") Long id, @CurrentUser User user) {
        caseService.deleteCase(id, user);
        return "redirect:/cases";
    }

    // 사건 수정하기 (첫 화면)
    @GetMapping(value = "/cases/update/{id}")
    public String createEditCase(Model model, @PathVariable("id") Long caseId, @CurrentUser User user) {
        Optional<Case> caseDto = caseService.findCase(caseId);

        if(caseDto.isPresent() && user.getId().equals(caseDto.get().getUser().getId())){
            CaseResponseDto caseResponseDto =  new CaseResponseDto(caseDto.get(), caseDto.get().getUser());
            model.addAttribute("caseDto", caseResponseDto);

            String redirectURL = "/cases/update/" + caseId.toString();
            model.addAttribute("RedirectURL",  redirectURL);
        }
        return "/cases/caseUpdate";
    }

    // 사건 수정하기 (제출)
    @PostMapping(value = "/cases/update/{id}")
    public String uploadEditCase(@Valid @ModelAttribute CaseSaveRequestDto caseDto, @PathVariable("id") Long caseId,
        @CurrentUser User user, Errors errors) throws Exception {
        if (errors.hasErrors()) {
            log.info("error!!");
            return "redirect:/cases";
        }

        caseService.updateCase(user.getId(), caseId, caseDto, caseDto.getMissingPics());
        return "redirect:/cases/detail/{id}";
    }

    // 실종자 찾았을 경우
    @GetMapping(value = "/cases/find/{id}")
    public String findCase(@PathVariable("id") Long caseId, @CurrentUser User user) {
        Optional<Case> caseEntity = caseService.findCase(caseId);

        if(caseEntity.isPresent() && user.getId().equals(caseEntity.get().getUser().getId())){
            caseService.foundCase(caseId,true);
        }

        return "redirect:/cases/detail/{id}";
    }

    @Transactional(readOnly = true)
    @GetMapping(value = "/cases/searchCase")
    public String searchCaseForm(SearchFormDto form, Model model, @PageableDefault(size=12) Pageable pageable,@RequestParam(value="page", defaultValue="0") int page, HttpServletRequest request) {
        System.out.println("redirect:  " + RequestContextUtils.getInputFlashMap(request));
        Optional<List<Case>> caseList = Optional.empty();
        if (RequestContextUtils.getInputFlashMap(request) != null){
            //System.out.println("redirect2:  " + RequestContextUtils.getInputFlashMap(request).values().stream().collect(Collectors.toList()).get(0));
            caseList = (Optional<List<Case>>) RequestContextUtils.getInputFlashMap(request).values().stream().collect(Collectors.toList()).get(0);

            if (caseList.get().isEmpty()){
                System.out.println("없음");
            } else {
                System.out.println(caseList.get().get(0).getCaseId());
                System.out.println("fileEntity: " + caseService.test(caseList.get().get(0).getCaseId()));
                for(int i = 0; i < caseList.get().size(); i++){
                    caseList.get().get(i).setMissingPics(caseService.test(caseList.get().get(i).getCaseId()));
                }
                //caseList.get().get(0).setMissingPics(caseService.test(caseList.get().get(0).getCaseId()));
            }
            //System.out.println(caseList.get().get(0).getMissingPics());
        } else {
            System.out.println("nono");
            caseList = caseService.sortCasebyTime();
        }

        if(caseList.isPresent()) {
            System.out.println(caseList.get());
             List<CaseListResponseDto> caseDtoList = caseList.get().stream().map(
                caseEntity -> new CaseListResponseDto(caseEntity, caseEntity.getUser())
            ).collect(Collectors.toList());

            // 페이징 변환 작업
            final int startPage = (int)pageable.getOffset();
            final int endPage = Math.min((startPage + pageable.getPageSize()), caseDtoList.size());
            Page<CaseListResponseDto> pagingDtoList = new PageImpl<>(caseDtoList.subList(startPage, endPage), pageable, caseDtoList.size());

            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("cases", pagingDtoList);
        } else {
            System.out.println("없음");
        }
        return "/cases/searchCaseForm";
    }

    @Transactional(readOnly = true)
    @PostMapping(value = "/cases/search/submit")
    public String showCaseForm(SearchFormDto form, Model model, RedirectAttributes redirectAttributes, @PageableDefault(size=12) Pageable pageable, @RequestParam(value="page", defaultValue="0") int page) {
        Optional<List<Case>> caseList = Optional.empty();
        Optional<List<String>> sex = form.getMissing_sex();
        Optional<List<String>> age = form.getMissing_age();
        Optional<List<String>> area = form.getMissing_area();
        String feature = form.getMissing_feature();
        //System.out.println(sex.toString() + age + area + feature);
        if (form.getSearch_type().equals("name")){ // 이름 입력 했을 때
            caseList = caseService.findbyMissingName(form.getMissing_name(), sex, age, area, feature);
        } else if (form.getSearch_type().equals("area")) { // 주소 입력 했을 때
            caseList = caseService.findbyMissingArea(form.getMissing_name(), sex, age, area, feature);
        } else { // 아무 입력값이 없을 때
            caseList = caseService.findbyFilters(form.getMissing_sex(), form.getMissing_age(), form.getMissing_area(), feature);
        }
        //Optional<Case> searchList = caseService.findOnebyMissingName(form.getMissing_name());
        if(caseList.isPresent()) {
            System.out.println(caseList.get());
        } else {
            System.out.println("없음");
        }
        redirectAttributes.addFlashAttribute("searchParam", caseList);
        return "redirect:/cases/searchCase";
       // return "/cases/searchCaseForm";
    }


    // (1) 장소 검색 (new Case)
    @GetMapping(value = "/searchPlace")
    public String searchPlace() {
        return "/cases/searchPlace";
    }
}
