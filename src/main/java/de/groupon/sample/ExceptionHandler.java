package de.groupon.sample;

import de.groupon.sample.exception.NotFoundException;
import de.groupon.sample.model.ErrorDetail;
import de.groupon.sample.model.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseError handleException(final MissingServletRequestParameterException exception) {
        String message = "Missing parameter " + exception.getParameterName() + " of type " + exception.getParameterType();
        return new ResponseError(new ErrorDetail(message));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseError handleException(final NotFoundException exception) {
        return new ResponseError(new ErrorDetail(exception.getMessage()));
    }

}
