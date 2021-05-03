package org.skb_lab.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserExceptions {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public static class RestException extends RuntimeException {
        public RestException() {
        }

        public RestException(String message) {
            super(message);
        }
    }
}
