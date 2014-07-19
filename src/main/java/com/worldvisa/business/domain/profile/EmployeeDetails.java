/******************************************************************************
 * Name : EmployeeDetails.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for EmployeeDetails
 *****************************************************************************/
package com.worldvisa.business.domain.profile;

import java.util.List;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;

/**
 * POJO implementation for EmployeeDetails.
 */
@DataTransferObject(converter = BeanConverter.class)
public class EmployeeDetails
extends LoginDetails {
    private static final long serialVersionUID = 1L;
    private Long              baseLocation;
    private Long              educationConverted;
    private Long              educationTotal;
    private Long              employeeId;
    private String            employeeName;
    private Long              immigrationConverted;
    private Long              immigrationTotal;
    private java.util.Date    joiningDate;
    private String            mobileNum;
    private Long              othersConverted;
    private Long              othersTotal;
    private List<String>      reportingManagers;

    /**
     * Instantiates EmployeeDetails with defaults
     */
    public EmployeeDetails() {
        super();
    }

    /**
     * Instantiates EmployeeDetails with primary key combination
     */
    public EmployeeDetails(final Long employeeId) {
        super();
        this.employeeId = employeeId;
    }

    /**
     * @return the baseLocation
     */
    public Long getBaseLocation() {
        return this.baseLocation;
    }

    /**
     * @return the educationConverted
     */
    public Long getEducationConverted() {
        return this.educationConverted;
    }

    /**
     * @return the educationTotal
     */
    public Long getEducationTotal() {
        return this.educationTotal;
    }

    /**
     * Getter method for employeeId
     * @return employeeId
     */
    public Long getEmployeeId() {
        return this.employeeId;
    }

    /**
     * Getter method for employeeName
     * @return employeeName
     */
    public String getEmployeeName() {
        return this.employeeName;
    }

    /**
     * @return the immigrationConverted
     */
    public Long getImmigrationConverted() {
        return this.immigrationConverted;
    }

    /**
     * @return the immigrationTotal
     */
    public Long getImmigrationTotal() {
        return this.immigrationTotal;
    }

    /**
     * Getter method for joiningDate
     * @return joiningDate
     */
    public java.util.Date getJoiningDate() {
        return this.joiningDate;
    }

    /**
     * Getter method for mobileNum
     * @return mobileNum
     */
    public String getMobileNum() {
        return this.mobileNum;
    }

    /**
     * @return the othersConverted
     */
    public Long getOthersConverted() {
        return this.othersConverted;
    }

    /**
     * @return the othersTotal
     */
    public Long getOthersTotal() {
        return this.othersTotal;
    }

    /**
     * @return the reportingManagers
     */
    public List<String> getReportingManagers() {
        return this.reportingManagers;
    }

    /**
     * @param baseLocation the baseLocation to set
     */
    public void setBaseLocation(final Long baseLocation) {
        this.baseLocation = baseLocation;
    }

    /**
     * @param educationConverted the educationConverted to set
     */
    public void setEducationConverted(final Long educationConverted) {
        this.educationConverted = educationConverted;
    }

    /**
     * @param educationTotal the educationTotal to set
     */
    public void setEducationTotal(final Long educationTotal) {
        this.educationTotal = educationTotal;
    }

    /**
     * Setter method for employeeId
     * @param employeeId Value for employeeId
     */
    public void setEmployeeId(final Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Setter method for employeeName
     * @param employeeName Value for employeeName
     */
    public void setEmployeeName(final String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * @param immigrationConverted the immigrationConverted to set
     */
    public void setImmigrationConverted(final Long immigrationConverted) {
        this.immigrationConverted = immigrationConverted;
    }

    /**
     * @param immigrationTotal the immigrationTotal to set
     */
    public void setImmigrationTotal(final Long immigrationTotal) {
        this.immigrationTotal = immigrationTotal;
    }

    /**
     * Setter method for joiningDate
     * @param joiningDate Value for joiningDate
     */
    public void setJoiningDate(final java.util.Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    /**
     * Setter method for mobileNum
     * @param mobileNum Value for mobileNum
     */
    public void setMobileNum(final String mobileNum) {
        this.mobileNum = mobileNum;
    }

    /**
     * @param othersConverted the othersConverted to set
     */
    public void setOthersConverted(final Long othersConverted) {
        this.othersConverted = othersConverted;
    }

    /**
     * @param othersTotal the othersTotal to set
     */
    public void setOthersTotal(final Long othersTotal) {
        this.othersTotal = othersTotal;
    }

    /**
     * @param reportingManagers the reportingManagers to set
     */
    public void setReportingManagers(final List<String> reportingManagers) {
        this.reportingManagers = reportingManagers;
    }
}
