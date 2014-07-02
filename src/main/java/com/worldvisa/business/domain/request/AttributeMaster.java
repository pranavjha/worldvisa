/******************************************************************************
 * Name : AttributeMaster.java Author : Administrator Date : 2010/05/25 Description : POJO implementation for AttributeMaster
 *****************************************************************************/
package com.worldvisa.business.domain.request;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for AttributeMaster.
 */
@DataTransferObject(converter = BeanConverter.class)
public class AttributeMaster
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private String            attributeDesc;
    private Long              attributeId;
    private String            inputType;
    private String            lookupId;
    private Long              reportType;
    private String            validationRegex;

    /**
     * Instantiates AttributeMaster with defaults
     */
    public AttributeMaster() {
        super();
    }

    /**
     * Instantiates AttributeMaster with primary key combination
     */
    public AttributeMaster(final Long attributeId, final Long reportType) {
        super();
        this.attributeId = attributeId;
        this.reportType = reportType;
    }

    /**
     * Getter method for attributeDesc
     * @return attributeDesc
     */
    public String getAttributeDesc() {
        return this.attributeDesc;
    }

    /**
     * Getter method for attributeId
     * @return attributeId
     */
    public Long getAttributeId() {
        return this.attributeId;
    }

    /**
     * Getter method for inputType
     * @return inputType
     */
    public String getInputType() {
        return this.inputType;
    }

    /**
     * @return the lookupId
     */
    public String getLookupId() {
        return this.lookupId;
    }

    /**
     * Getter method for reportType
     * @return reportType
     */
    public Long getReportType() {
        return this.reportType;
    }

    /**
     * Getter method for validationRegex
     * @return validationRegex
     */
    public String getValidationRegex() {
        return this.validationRegex;
    }

    /**
     * Setter method for attributeDesc
     * @param attributeDesc Value for attributeDesc
     */
    public void setAttributeDesc(final String attributeDesc) {
        this.attributeDesc = attributeDesc;
    }

    /**
     * Setter method for attributeId
     * @param attributeId Value for attributeId
     */
    public void setAttributeId(final Long attributeId) {
        this.attributeId = attributeId;
    }

    /**
     * Setter method for inputType
     * @param inputType Value for inputType
     */
    public void setInputType(final String inputType) {
        this.inputType = inputType;
    }

    /**
     * @param lookupId the lookupId to set
     */
    public void setLookupId(final String lookupId) {
        this.lookupId = lookupId;
    }

    /**
     * Setter method for reportType
     * @param reportType Value for reportType
     */
    public void setReportType(final Long reportType) {
        this.reportType = reportType;
    }

    /**
     * Setter method for validationRegex
     * @param validationRegex Value for validationRegex
     */
    public void setValidationRegex(final String validationRegex) {
        this.validationRegex = validationRegex;
    }
}
