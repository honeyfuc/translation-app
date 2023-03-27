package com.yaroslavyankov.translation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SameSourceAndTargetLanguages extends Exception {
    public SameSourceAndTargetLanguages(String message) {
        super(message);
    }
}
