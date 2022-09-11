package com.typetest.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HttpErrorController implements ErrorController {

    @RequestMapping("/error")
    public String returnErrorPage(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int code = Integer.parseInt(status.toString());
            ErrorInfoDto errorInfoDto = new ErrorInfoDto();
            switch (code) {
                case 400:
                    errorInfoDto.setErrorCode(400);
                    errorInfoDto.setErrorName("Bad Request");
                    errorInfoDto.setErrorMessage("잘못된 요청이에요 (˚ ˃̣̣̥⌓˂̣̣̥ )");
                    break;
                case 401:
                    errorInfoDto.setErrorCode(401);
                    errorInfoDto.setErrorName("Unauthorized");
                    errorInfoDto.setErrorMessage("인증되지 않은 사용자에요 (˚ ˃̣̣̥⌓˂̣̣̥ )");
                    break;
                case 403:
                    errorInfoDto.setErrorCode(403);
                    errorInfoDto.setErrorName("Forbidden");
                    errorInfoDto.setErrorMessage("접근 권한이 없어요 (˚ ˃̣̣̥⌓˂̣̣̥ )");
                    break;
                case 404:
                    errorInfoDto.setErrorCode(404);
                    errorInfoDto.setErrorName("Not Found");
                    errorInfoDto.setErrorMessage("페이지를 찾을 수 없어요 (˚ ˃̣̣̥⌓˂̣̣̥ )");
                    break;
                case 500:
                    errorInfoDto.setErrorCode(500);
                    errorInfoDto.setErrorName("Internal Server Error");
                    errorInfoDto.setErrorMessage("서버 에러가 발생했어요. (˚ ˃̣̣̥⌓˂̣̣̥ )");
                    errorInfoDto.setErrorMessage2("증상이 지속되면 관리자에게 문의해주세요!");
                    break;
                case 503:
                    errorInfoDto.setErrorCode(503);
                    errorInfoDto.setErrorName("Service Unavailable");
                    errorInfoDto.setErrorMessage("서비스가 일시적으로 이용할 수 없는 상태입니다. (˚ ˃̣̣̥⌓˂̣̣̥ )");
                    errorInfoDto.setErrorMessage2("잠시 후 다시 시도해주세요!");
                    break;
                default:
                    errorInfoDto.setErrorCode(code);
                    errorInfoDto.setErrorName("Unknown Error");
                    errorInfoDto.setErrorMessage("알 수 없는 오류가 발생했어요! (˚ ˃̣̣̥⌓˂̣̣̥ )");
            }
            model.addAttribute("errorInfo", errorInfoDto);
        }
        return "errors/errorPage";
    }
}
