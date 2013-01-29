package de.groupon.sample.exception;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

public class InvalidParamException extends Exception {

    public InvalidParamException() {
        super();
    }

    public InvalidParamException(String s) {
        super(s);
    }

    public InvalidParamException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidParamException(Throwable throwable) {
        super(throwable);
    }
}
