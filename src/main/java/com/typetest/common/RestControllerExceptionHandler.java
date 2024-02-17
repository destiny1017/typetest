package com.typetest.common;

import com.typetest.common.constant.ErrorCode;
import com.typetest.common.exception.ErrorResponse;
import com.typetest.common.exception.TypetestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> customExceptionHandler(Exception e) {
        log.error("An Error occurred..", e);
        ErrorResponse response = ErrorResponse.builder()
                .status(ErrorCode.SERVER_ERROR.getHttpStatus().value())
                .message(e.getMessage())
                .code(ErrorCode.SERVER_ERROR.name())
                .build();
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler(TypetestException.class)
    protected ResponseEntity<ErrorResponse> typetestExceptionHandler(TypetestException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode(), String.join(",", e.getKeys()));
    }
}
