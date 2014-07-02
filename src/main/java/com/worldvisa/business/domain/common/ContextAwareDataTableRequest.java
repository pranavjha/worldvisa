package com.worldvisa.business.domain.common;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;

@DataTransferObject(converter = BeanConverter.class)
public class ContextAwareDataTableRequest
extends DataTableRequest {
    private static final long serialVersionUID = -4479765459595100262L;
    private String            emailId;

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return this.emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }
}
