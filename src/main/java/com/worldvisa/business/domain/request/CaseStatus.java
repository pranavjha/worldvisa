/******************************************************************************
 * Name : FollowupStatus.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for FollowupStatus
 *****************************************************************************/
package com.worldvisa.business.domain.request;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.domain.workflow.PaymentOption;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for FollowupStatus.
 */
@DataTransferObject(converter = BeanConverter.class)
public class CaseStatus
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private java.util.Date    addedDate;
    private Long              caseStatusId;
    private String            description;
    private PaymentOption     paymentOption;
    private ReportDetails     reportDetails;
    private String            reportId;
    private String            setBy;
    private Long              substatus;

    /**
     * Instantiates FollowupStatus with defaults
     */
    public CaseStatus() {
        super();
    }

    /**
     * Instantiates FollowupStatus with primary key combination
     */
    public CaseStatus(final Long caseStatusId, final String reportId) {
        super();
        this.caseStatusId = caseStatusId;
        this.reportId = reportId;
    }

    /**
     * Getter method for addedDate
     * @return addedDate
     */
    public java.util.Date getAddedDate() {
        return this.addedDate;
    }

    /**
     * Getter method for caseStatusId
     * @return caseStatusId
     */
    public Long getCaseStatusId() {
        return this.caseStatusId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return the paymentOption
     */
    public PaymentOption getPaymentOption() {
        return this.paymentOption;
    }

    /**
     * @return the reportDetails
     */
    public ReportDetails getReportDetails() {
        return this.reportDetails;
    }

    /**
     * Getter method for reportId
     * @return reportId
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * Getter method for setBy
     * @return setBy
     */
    public String getSetBy() {
        return this.setBy;
    }

    /**
     * Getter method for substatus
     * @return substatus
     */
    public Long getSubstatus() {
        return this.substatus;
    }

    /**
     * Setter method for addedDate
     * @param addedDate Value for addedDate
     */
    public void setAddedDate(final java.util.Date addedDate) {
        this.addedDate = addedDate;
    }

    /**
     * Setter method for caseStatusId
     * @param caseStatusId Value for caseStatusId
     */
    public void setCaseStatusId(final Long caseStatusId) {
        this.caseStatusId = caseStatusId;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @param paymentOption the paymentOption to set
     */
    public void setPaymentOption(final PaymentOption paymentOption) {
        this.paymentOption = paymentOption;
    }

    /**
     * @param reportDetails the reportDetails to set
     */
    public void setReportDetails(final ReportDetails reportDetails) {
        this.reportDetails = reportDetails;
    }

    /**
     * Setter method for reportId
     * @param reportId Value for reportId
     */
    public void setReportId(final String reportId) {
        this.reportId = reportId;
    }

    /**
     * Setter method for setBy
     * @param setBy Value for setBy
     */
    public void setSetBy(final String setBy) {
        this.setBy = setBy;
    }

    /**
     * Setter method for substatus
     * @param substatus Value for substatus
     */
    public void setSubstatus(final Long substatus) {
        this.substatus = substatus;
    }
}
