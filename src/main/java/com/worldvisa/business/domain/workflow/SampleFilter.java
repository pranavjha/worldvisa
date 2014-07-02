package com.worldvisa.business.domain.workflow;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.domain.common.DataTableRequest;

@DataTransferObject(converter = BeanConverter.class)
public class SampleFilter
extends DataTableRequest {
    private static final long serialVersionUID = 1L;
    private String            apsNumber;
    private String            destination;
    private String            po;
    private String            status;

    public String getApsNumber() {
        return this.apsNumber;
    }

    public String getDestination() {
        return this.destination;
    }

    public String getPo() {
        return this.po;
    }

    public String getStatus() {
        return this.status;
    }

    public void setApsNumber(String apsNumber) {
        this.apsNumber = apsNumber;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
