package de.groupon.sample.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class ResponseError {
    private ErrorDetail error;

    public ResponseError(ErrorDetail error) {
        this.error = error;
    }

    public ErrorDetail getError() {
        return error;
    }

    public void setError(ErrorDetail error) {
        this.error = error;
    }
}
