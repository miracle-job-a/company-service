package com.miracle.companyservice.exception;

import java.util.NoSuchElementException;

public class NoSuchPostException extends NoSuchElementException {
    public NoSuchPostException(String s) {
        super(s);
    }
}
