package com.ta.csc.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyExistsException  extends RuntimeException{

    Logger LOG = LoggerFactory.getLogger(NotFoundException.class);

    public AlreadyExistsException(String message) {
        super(message);
        LOG.error(message);
    }
}
