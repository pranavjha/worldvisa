/******************************************************************************
 * Name : ReportAttributes.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for ReportAttributes
 *****************************************************************************/
package com.worldvisa.business.domain.request;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for ReportAttributes.
 */
@DataTransferObject(converter = BeanConverter.class)
public class ReportAttributes
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private String            atributeValue;
    private AttributeMaster   attributeDetails;
    private Long              attributeId;
    private String            reportId;

    /**
     * Instantiates ReportAttributes with defaults
     */
    public ReportAttributes() {
        super();
    }

    /**
     * Instantiates ReportAttributes with primary key combination
     */
    public ReportAttributes(final Long attributeId, final String reportId) {
        super();
        this.reportId = reportId;
        this.attributeId = attributeId;
    }

    /**
     * @return the atributeValue
     */
    public String getAtributeValue() {
        return this.atributeValue;
    }

    /**
     * @return the attributeDetails
     */
    public AttributeMaster getAttributeDetails() {
        return this.attributeDetails;
    }

    /**
     * @return the attributeId
     */
    public Long getAttributeId() {
        return this.attributeId;
    }

    /**
     * @return the reportId
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * @param atributeValue the atributeValue to set
     */
    public void setAtributeValue(final String atributeValue) {
        this.atributeValue = atributeValue;
    }

    /**
     * @param attributeDetails the attributeDetails to set
     */
    public void setAttributeDetails(final AttributeMaster attributeDetails) {
        this.attributeDetails = attributeDetails;
    }

    /**
     * @param attributeId the attributeId to set
     */
    public void setAttributeId(final Long attributeId) {
        this.attributeId = attributeId;
    }

    /**
     * @param reportId the reportId to set
     */
    public void setReportId(final String reportId) {
        this.reportId = reportId;
    }
}
