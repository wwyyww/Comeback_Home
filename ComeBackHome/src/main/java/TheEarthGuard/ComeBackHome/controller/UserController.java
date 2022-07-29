package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.dto.UserDto;
import TheEarthGuard.ComeBackHome.security.CheckEmailValidator;
import TheEarthGuard.ComeBackHome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserController {
    private final UserService userService;
    private final CheckEmailValidator checkEmailValidator;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkEmailValidator);
    }

    @Autowired
    public UserController(UserService userService, CheckEmailValidator checkEmailValidator) {
        this.userService = userService;
        this.checkEmailValidator = checkEmailValidator;
    }

    @GetMapping("/users/signup")
    public String signUpForm(UserDto userDto) {
        return "users/signup";
    }

    @PostMapping("/users/signup")
    public String signUp(@Valid UserDto userDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("userDto", userDto);

            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
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

    @GetMapping("/users/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }

    @GetMapping("/users/kakao")
    public String kakao(String code) {

        return "redirect:/";
    }

}
