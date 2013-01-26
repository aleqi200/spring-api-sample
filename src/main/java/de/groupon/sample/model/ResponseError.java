package de.groupon.sample.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "response")
@XmlType(name="ResponseError")
public class ResponseError {
    private ErrorDetail error;

    public ResponseError() {
    }

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
