package de.groupon.sample.model;

public class ErrorDetail {
    private String message;
    private String detail;

    public ErrorDetail() {
    }

    public ErrorDetail(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
