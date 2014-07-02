package com.worldvisa.business.domain.common;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;

@DataTransferObject(converter = BeanConverter.class)
public class WebMessage {
    private String details;
    private String message;
    private String severity;

    public WebMessage() {
    }

    public WebMessage(final String message, final String severity) {
        this.message = this.details = message;
        this.severity = severity;
    }

    public WebMessage(final String message, final String details, final String severity) {
        this.message = message;
        this.details = details;
        this.severity = severity;
    }

    public String getDetails() {
        return this.details;
    }

    public String getMessage() {
        return this.message;
    }

    public String getSeverity() {
        return this.severity;
    }

    public void setDetails(final String details) {
        this.details = details;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setSeverity(final String severity) {
        this.severity = severity;
    }
}
