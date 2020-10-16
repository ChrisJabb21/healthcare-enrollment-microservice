package com.challenge.enrollment.enrolleeservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class EntityNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 5876916510111669821L;

    EntityNotFoundException(String message) {
        super(message);
    }
}
