package de.groupon.sample.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "response")
@XmlType(name="ResponseError")
public class ResponseError {

    private List<ErrorDetail> error;

    public ResponseError() {
        error = new ArrayList<ErrorDetail>();
    }

    public ResponseError(ErrorDetail error) {
        this();
        this.error.add(error);
    }

    public List<ErrorDetail> getError() {
        return error;
    }

    public void setError(List<ErrorDetail> error) {
        this.error = error;
    }
}
