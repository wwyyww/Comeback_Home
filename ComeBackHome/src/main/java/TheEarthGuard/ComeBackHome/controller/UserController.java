package TheEarthGuard.ComeBackHome.controller;

import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import javax.validation.ValidationException;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/signup")
    public String signUpForm() {
        return "users/signup";
    }

    @PostMapping("/users/signup")
    public String signUp(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
//            HashMap<String, String> errorMap = new HashMap<>();
//            for (FieldError er : bindingResult.getFieldErrors()) {
//                errorMap.put(er.getField(), er.getDefaultMessage());
//            }
            throw new ValidationException("유효성 검사 실패");
        } else {
            userService.signUp(user);
            return "redirect:/";
        }

    }

    @GetMapping("/users/login")
    public String loginForm() {
        return "users/login";
    }


}
