package TheEarthGuard.ComeBackHome.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private String ERROR_PATH = "/error/";

    @GetMapping(value = "/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return ERROR_PATH + "404";
            }

            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return ERROR_PATH + "500";
            }
        }
        return ERROR_PATH + "404";

    }

}
