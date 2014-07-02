/******************************************************************************
 * Name : WorkExperience.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for WorkExperience
 *****************************************************************************/
package com.worldvisa.business.domain.request;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for WorkExperience.
 */
@DataTransferObject(converter = BeanConverter.class)
public class WorkExperience
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private String            designation;
    private String            employer;
    private java.util.Date    fromDate;
    private String            reportId;
    private Long              seqNum;
    private java.util.Date    toDate;

    /**
     * Instantiates WorkExperience with defaults
     */
    public WorkExperience() {
        super();
    }

    /**
     * Instantiates WorkExperience with primary key combination
     */
    public WorkExperience(final String reportId, final Long seqNum) {
        super();
        this.reportId = reportId;
        this.seqNum = seqNum;
    }

    /**
     * @return the designation
     */
    public String getDesignation() {
        return this.designation;
    }

    /**
     * @return the employer
     */
    public String getEmployer() {
        return this.employer;
    }

    /**
     * @return the fromDate
     */
    public java.util.Date getFromDate() {
        return this.fromDate;
    }

    /**
     * @return the reportId
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * @return the seqNum
     */
    public Long getSeqNum() {
        return this.seqNum;
    }

    /**
     * @return the toDate
     */
    public java.util.Date getToDate() {
        return this.toDate;
    }

    /**
     * @param designation the designation to set
     */
    public void setDesignation(final String designation) {
        this.designation = designation;
    }

    /**
     * @param employer the employer to set
     */
    public void setEmployer(final String employer) {
        this.employer = employer;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(final java.util.Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @param reportId the reportId to set
     */
    public void setReportId(final String reportId) {
        this.reportId = reportId;
    }

    /**
     * @param seqNum the seqNum to set
     */
    public void setSeqNum(final Long seqNum) {
        this.seqNum = seqNum;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(final java.util.Date toDate) {
        this.toDate = toDate;
    }
}
