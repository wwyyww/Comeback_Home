package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.dto.UserDto;
import TheEarthGuard.ComeBackHome.security.CurrentUser;
import TheEarthGuard.ComeBackHome.service.UserService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class UserController {
    private final UserService userService;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators();
    }



    @Autowired
    @SuppressFBWarnings(justification = "Generated code")
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/signup")
    public String signUpForm(UserDto userDto) {
        return "users/signup";
    }

    @PostMapping("/users/signup")
    public String signUp(@Valid UserDto userDto, Errors errors, Model model) {
        if (!userService.validateEmail(userDto.getEmail())) {
            model.addAttribute("userDto", userDto);
            model.addAttribute("valid_email", "이미 사용중인 이메일입니다.");
            return "users/signup";
        }

        if (errors.hasErrors()) {
            model.addAttribute("userDto", userDto);

            Map<String, String> validatorResult = userService.validateHandling(errors);

            for (Map.Entry<String,String> entry : validatorResult.entrySet()){
                model.addAttribute(entry.getKey(), entry.getValue());
            }
            return "users/signup";
        }
        userService.signUp(userDto);
        return "redirect:/";
    }

    @GetMapping("/users/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/users/login";
    }

    @GetMapping("/users/loginfail")
    public String loginFail(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/users/login";
    }

    @GetMapping("/users/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

//    @GetMapping("/users/mypage")
//    public String myPage(@CurrentUser User user) {
//        if (user != null) {
//            return "/users/mypage";
//        }
//        return "/users/login";
//    }

    @GetMapping("/mypage/edit")
    public String myPageEditForm(@CurrentUser User user, UserDto userEntity, Model model, Errors errors) {
        UserDto userDto = new UserDto();
        User newUser = userService.findById(user.getId()).orElseThrow();
        userDto.setPw(newUser.getPw());
        userDto.setEmail(newUser.getEmail());
        userDto.setBirth(newUser.getBirth());
        userDto.setPhone(newUser.getPhone());
        userDto.setName(newUser.getName());
        userDto.setSex(newUser.getSex());
        model.addAttribute("userDto", userDto);

        if (errors.hasErrors()) {
            return "/";
        }


        return "/users/myedit";
    }

    @PostMapping("/mypage/edit")
    public String updateUserInfo(@Valid UserDto userDto, Errors errors, Model model, @CurrentUser User user) {

        if (errors.hasErrors()) {
            log.info("error!!");
            model.addAttribute("userDto", userDto);

            Map<String, String> validatorResult = userService.validateHandling(errors);

            for (Map.Entry<String,String> entry : validatorResult.entrySet()){
                model.addAttribute(entry.getKey(), entry.getValue());
            }

            return "/users/myedit";
        }

        userService.updateUser(user, userDto);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User userAccount = (User)authentication.getPrincipal();
//        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
//                authentication.getCredentials(), userAccount.getAuthorities());
//        newAuth.setDetails(authentication.getDetails());
//        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return "redirect:/users/mypage";
    }

}
