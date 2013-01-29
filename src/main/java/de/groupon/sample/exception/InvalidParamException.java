package de.groupon.sample.exception;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

public class InvalidParamException extends Exception {

    private Errors errors;

    public InvalidParamException(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String getMessage() {
        StringBuffer message = new StringBuffer();
        final List<FieldError> fieldErrors = errors.getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            message.append(fieldError.getDefaultMessage());
        }
        return message.toString();
    }
}
