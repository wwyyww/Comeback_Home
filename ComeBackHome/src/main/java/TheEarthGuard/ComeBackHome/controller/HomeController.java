package TheEarthGuard.ComeBackHome.controller;


import TheEarthGuard.ComeBackHome.service.CaseService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Scanner;

@Slf4j
@Controller
public class HomeController {
    private final CaseService caseService;

    @SuppressFBWarnings(justification = "Generated code")
    public HomeController(CaseService caseService) {
        this.caseService = caseService;
    }

//    @GetMapping("/")
//    public String home(@CurrentUser User user) {
//        if (user != null) {
//            log.info("사용자 정보 : "+user);
//            log.info("사용자 이메일 : "+user.getEmail());
//        }
//
//        return "home";
//    }

    @GetMapping("/mapTest")
    public String mapTest(Model model) {


        return "mapTest";
    }

}
