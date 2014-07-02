package com.worldvisa.business.infra.exception;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.Param;
import org.directwebremoting.convert.ExceptionConverter;
import com.worldvisa.business.infra.base.BaseException;

@DataTransferObject(converter = ExceptionConverter.class, params = {
    @Param(name = "include", value = "message,severity,type")
})
public class DWRException
extends BaseException {
    private static final long serialVersionUID = 6305994518409757046L;
    private String            message;
    private String            severity;

    public DWRException() {
    }

    public DWRException(final String message, final String severity) {
        this.message = message;
        this.severity = severity.toString();
    }

    public DWRException(final Throwable th, final String severity) {
        super(th);
        this.message = th.getMessage();
        this.severity = severity;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public String getSeverity() {
        return this.severity;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
