/******************************************************************************
 * Name : EmployeeManagementFilter.java Author : Administrator Date : 17-May-2010,8:08:18 PM Description : Implementation for EmployeeManagementFilter
 *****************************************************************************/
package com.worldvisa.business.domain.profile;

import java.util.Date;
import java.util.List;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.domain.common.DataTableRequest;

/**
 * 
 */
@DataTransferObject(converter = BeanConverter.class)
public class EmployeeManagementFilter
extends DataTableRequest {
    private static final long serialVersionUID = -1191332533565961694L;
    private Long              baseLocation;
    private String            eMail;
    private String            employeeId;
    private List<Date>        joiningDates;
    private String            mobileNum;
    private String            name;
    private String            reportsTo;

    /**
     * @return the baseLocation
     */
    public Long getBaseLocation() {
        return this.baseLocation;
    }

    /**
     * @return the eMail
     */
    public String geteMail() {
        return this.eMail;
    }

    /**
     * @return the employeeId
     */
    public String getEmployeeId() {
        return this.employeeId;
    }

    /**
     * @return the joiningDates
     */
    public List<Date> getJoiningDates() {
        return this.joiningDates;
    }

    /**
     * @return the mobileNum
     */
    public String getMobileNum() {
        return this.mobileNum;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the reportsTo
     */
    public String getReportsTo() {
        return this.reportsTo;
    }

    /**
     * @param baseLocation the baseLocation to set
     */
    public void setBaseLocation(final Long baseLocation) {
        this.baseLocation = baseLocation;
    }

    /**
     * @param eMail the eMail to set
     */
    public void seteMail(final String eMail) {
        this.eMail = eMail;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(final String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @param joiningDates the joiningDates to set
     */
    public void setJoiningDates(final List<Date> joiningDates) {
        this.joiningDates = joiningDates;
    }

    /**
     * @param mobileNum the mobileNum to set
     */
    public void setMobileNum(final String mobileNum) {
        this.mobileNum = mobileNum;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @param reportsTo the reportsTo to set
     */
    public void setReportsTo(final String reportsTo) {
        this.reportsTo = reportsTo;
    }
}
