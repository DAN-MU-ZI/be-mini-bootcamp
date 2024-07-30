package com.study.bootcamp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {
    private static final Long SERIAL_VERSION_UID = 1L;

    public DataNotFoundException(String message) {
        super(message);
    }
}
