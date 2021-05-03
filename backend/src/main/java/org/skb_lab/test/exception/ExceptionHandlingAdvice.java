package org.skb_lab.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Logger;


@ControllerAdvice
public class ExceptionHandlingAdvice {
    private final Logger log = Logger.getLogger(getClass().getName());

    public ExceptionHandlingAdvice() {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleBadRequestException(UserExceptions.RestException e) {
        log.warning("Error: " + e.getMessage());
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getLocalizedMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ErrorMessage handleNotAllowedException(HttpRequestMethodNotSupportedException e) {
        log.warning("Error: " + e.getMessage());
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), "Page not found! Check link or try later!");
    }

}
