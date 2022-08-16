package TheEarthGuard.ComeBackHome.security;

import TheEarthGuard.ComeBackHome.domain.Role;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.domain.UserAdapter;
import TheEarthGuard.ComeBackHome.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final UserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        setDefaultTargetUrl("/");
        UserAdapter principal = (UserAdapter) authentication.getPrincipal();
        User user = principal.getUser();
        User findUser = userService.findByEmail(user.getEmail());
        if (findUser.getFail_cnt() >= 5) {
            throw new LockedException("계정이 잠겼습니다. 비밀번호 찾기 후 로그인해주세요.");
        } else {
            findUser.setFail_cnt(0);
        }

        log.info("role : "+findUser.getRole());

        if (findUser.getRole().equals("ADMIN")) {
            log.info("getrole : " + Role.ADMIN.getValue());
            redirectStrategy.sendRedirect(request, response, "/admin");
        }else{
            SavedRequest savedRequest = requestCache.getRequest(request, response);
            if (savedRequest != null) {
                redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
            }else{
                redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
            }

        }




    }
}