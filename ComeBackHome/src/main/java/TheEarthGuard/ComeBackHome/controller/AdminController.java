package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class AdminController  {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/admin")
    public String adminPage() {

        return "/admin/admin_main";
    }

    @GetMapping("/admin/userList")
    public String adminUserList(Model model) {
        List<User> userList = userService.getUserList();
        log.info("userList "+userList.get(0));
        model.addAttribute("userList", userList);

        return "/admin/userList";
    }




}
