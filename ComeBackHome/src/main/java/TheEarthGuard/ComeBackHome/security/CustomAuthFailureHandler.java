package TheEarthGuard.ComeBackHome.security;

import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
@RequiredArgsConstructor
@Transactional
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMsg = null;

        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            String email = request.getParameter("email");
            User findUser = userService.findByEmail(email);

            if (findUser == null) {
                throw new IllegalArgumentException("잘못된 요청입니다.");
            }
            userService.addFailCnt(findUser);

            if (findUser.getFail_cnt() >= 5) {
                errorMsg = "계정이 비활성화 되었습니다.";
            } else {
                errorMsg = "아이디 또는 비밀번호가 맞지 않습니다.";
            }
        } else if (exception instanceof DisabledException) {
            errorMsg = "계정이 비활성화 되었습니다. 비밀번호 찾기를 통해 비밀번호를 변경 후 로그인해야합니다.";
        } else if (exception instanceof LockedException) {
            errorMsg = "계정이 비활성화 되었습니다. 비밀번호 찾기를 통해 비밀번호를 변경 후 로그인해야합니다.";
        }
        errorMsg= URLEncoder.encode(errorMsg,"UTF-8");//한글 인코딩 깨지는 문제 방지
        setDefaultFailureUrl("/users/loginfail?error=true&exception=" + errorMsg);
        super.onAuthenticationFailure(request, response, exception);

    }

}
