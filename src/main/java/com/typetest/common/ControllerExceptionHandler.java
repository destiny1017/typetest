package com.typetest.common;

import com.typetest.error.ErrorInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(HttpServletRequest request, Model model, Exception e) {
        log.error("An error occurred..", e);
        ErrorInfoDto errorInfoDto = new ErrorInfoDto();
        errorInfoDto.setErrorCode(500);
        errorInfoDto.setErrorName("Internal Server Error");
        errorInfoDto.setErrorMessage("서버 에러가 발생했어요. (˚ ˃̣̣̥⌓˂̣̣̥ )");
        errorInfoDto.setErrorMessage2("증상이 지속되면 관리자에게 문의해주세요!");
        model.addAttribute("errorInfo", errorInfoDto);
        return "errors/errorPage";
    }
}
