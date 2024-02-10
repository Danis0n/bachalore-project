package ru.fp.billingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedFormat extends RuntimeException {
    public UnsupportedFormat(String msg) {
        super(msg);
    }
}
