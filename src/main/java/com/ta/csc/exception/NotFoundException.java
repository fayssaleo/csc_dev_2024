package com.ta.csc.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    Logger LOG = LoggerFactory.getLogger(NotFoundException.class);

    public NotFoundException(String message) {
        super(message);
        LOG.error("Not Found error exception : {}", message);
    }
}
