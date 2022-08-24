package TheEarthGuard.ComeBackHome.controller;


import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.security.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

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
    public String mapTest() {

        return "mapTest";
    }

}
