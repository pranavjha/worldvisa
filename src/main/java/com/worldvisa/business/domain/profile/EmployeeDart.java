/******************************************************************************
 * Name : EmployeeDart.java Author : Administrator Date : 2010/05/26 Description : POJO implementation for EmployeeDart
 *****************************************************************************/
package com.worldvisa.business.domain.profile;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for EmployeeDart.
 */
@DataTransferObject(converter = BeanConverter.class)
public class EmployeeDart
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private Long              dartType;
    private String            description;
    private String            employeeEmail;
    private java.util.Date    reportDate;
    private String            reportId;
    private Long              seqNum;

    /**
     * Instantiates EmployeeDart with defaults
     */
    public EmployeeDart() {
        super();
    }

    /**
     * Instantiates EmployeeDart with primary key combination
     */
    public EmployeeDart(final String employeeEmail, final java.util.Date reportDate, final Long seqNum) {
        super();
        this.employeeEmail = employeeEmail;
        this.reportDate = reportDate;
        this.seqNum = seqNum;
    }

    /**
     * @return the dartType
     */
    public Long getDartType() {
        return this.dartType;
    }

    /**
     * Getter method for description
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Getter method for employeeId
     * @return employeeId
     */
    public String getEmployeeEmail() {
        return this.employeeEmail;
    }

    /**
     * Getter method for reportDate
     * @return reportDate
     */
    public java.util.Date getReportDate() {
        return this.reportDate;
    }

    /**
     * @return the reportId
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * Getter method for seqNum
     * @return seqNum
     */
    public Long getSeqNum() {
        return this.seqNum;
    }

    /**
     * @param dartType the dartType to set
     */
    public void setDartType(final Long dartType) {
        this.dartType = dartType;
    }

    /**
     * Setter method for description
     * @param description Value for description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Setter method for employeeId
     * @param employeeId Value for employeeId
     */
    public void setEmployeeEmail(final String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    /**
     * Setter method for reportDate
     * @param reportDate Value for reportDate
     */
    public void setReportDate(final java.util.Date reportDate) {
        this.reportDate = reportDate;
    }

    /**
     * @param reportId the reportId to set
     */
    public void setReportId(final String reportId) {
        this.reportId = reportId;
    }

    /**
     * Setter method for seqNum
     * @param seqNum Value for seqNum
     */
    public void setSeqNum(final Long seqNum) {
        this.seqNum = seqNum;
    }
}
