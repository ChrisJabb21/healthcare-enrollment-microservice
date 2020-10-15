package com.challenge.enrollment.enrolleeservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class EnrolleeNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 5876916510111669821L;

    EnrolleeNotFoundException(String message) {
        super(message);
    }
}
