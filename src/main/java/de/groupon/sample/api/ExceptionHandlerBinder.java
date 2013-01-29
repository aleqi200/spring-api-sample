package de.groupon.sample.api;

import de.groupon.sample.exception.AlreadyExistsException;
import de.groupon.sample.exception.InvalidParamException;
import de.groupon.sample.exception.NotFoundException;
import de.groupon.sample.model.ErrorDetail;
import de.groupon.sample.model.ResponseError;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

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
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseError handleException(final NotFoundException exception) {
        return new ResponseError(new ErrorDetail(exception.getMessage()));
    }

    @ExceptionHandler(InvalidParamException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseError handleException(final InvalidParamException exception) {
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

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ResponseError handleRuntimeException(HttpMediaTypeNotAcceptableException exception) {
        return new ResponseError(new ErrorDetail(exception.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public ResponseError handleRuntimeException(HttpMediaTypeNotSupportedException exception) {
        return new ResponseError(new ErrorDetail(exception.getMessage()));
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseError handleRuntimeException(HttpMessageNotReadableException exception) {
        return new ResponseError(new ErrorDetail(exception.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ResponseError handleRuntimeException(HttpRequestMethodNotSupportedException exception) {
        return new ResponseError(new ErrorDetail(exception.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseError handleRuntimeException(MissingServletRequestPartException exception) {
        return new ResponseError(new ErrorDetail(exception.getMessage()));
    }

    @ExceptionHandler(NoSuchRequestHandlingMethodException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseError handleRuntimeException(NoSuchRequestHandlingMethodException exception) {
        return new ResponseError(new ErrorDetail(exception.getMessage()));
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseError handleRuntimeException(TypeMismatchException exception) {
        return new ResponseError(new ErrorDetail(exception.getMessage()));
    }
}
