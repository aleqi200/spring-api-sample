package de.groupon.sample.api;

import de.groupon.sample.exception.AlreadyExistsException;
import de.groupon.sample.exception.InvalidParamException;
import de.groupon.sample.exception.NotFoundException;
import de.groupon.sample.model.ErrorDetail;
import de.groupon.sample.model.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ExceptionHandlerBinder {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseError handleException(final MissingServletRequestParameterException exception) {
        String message = "Missing parameter " + exception.getParameterName() + " of type " + exception.getParameterType();
        return new ResponseError(new ErrorDetail(message));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseError handleException(final NotFoundException exception) {
        return new ResponseError(new ErrorDetail(exception.getMessage()));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseError handleException(final AlreadyExistsException exception) {
        return new ResponseError(new ErrorDetail(exception.getMessage()));
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseError handleBindException(BindException invalidParamsEx) {
        String field = invalidParamsEx.getAllErrors().get(0).getObjectName();
        String defaultMessage = invalidParamsEx.getAllErrors().get(0).getDefaultMessage();
        final ErrorDetail errorDetail = new ErrorDetail("Parameter [" + field + "] is invalid.");
        errorDetail.setDetail(defaultMessage);
        return new ResponseError(errorDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseError handleBindException(MethodArgumentNotValidException invalidParamsEx) {
        final List<FieldError> fieldErrors = invalidParamsEx.getBindingResult().getFieldErrors();
        final ResponseError responseError = new ResponseError();

        for (FieldError fieldError : fieldErrors) {
            String field = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            final ErrorDetail errorDetail = new ErrorDetail("Parameter [" + field + "] is invalid.");
            errorDetail.setDetail(defaultMessage);
            responseError.getError().add(errorDetail);
        }

        return responseError;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseError handleRuntimeException(RuntimeException exception) {
        return new ResponseError(new ErrorDetail(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseError handleRuntimeException(Exception exception) {
        return new ResponseError(new ErrorDetail(exception.getMessage()));
    }

}
