/******************************************************************************
 * Name : EmployeePerformanceFilter.java Author : Administrator Date : 18-May-2010,10:09:18 PM Description : Implementation for
 * EmployeePerformanceFilter
 *****************************************************************************/
package com.worldvisa.business.domain.profile;

import java.util.Date;
import java.util.List;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * 
 */
@DataTransferObject(converter = BeanConverter.class)
public class EmployeePerformanceFilter
extends BaseBean {
    private static final long serialVersionUID = -2167331863006670429L;
    private List<Date>        dateRange;
    private String            employeeEmail;
    private Long              requestType;

    /**
     * @return the dateRange
     */
    public List<Date> getDateRange() {
        return this.dateRange;
    }

    /**
     * @return the employeeEmail
     */
    public String getEmployeeEmail() {
        return this.employeeEmail;
    }

    /**
     * @return the requestType
     */
    public Long getRequestType() {
        return this.requestType;
    }

    /**
     * @param dateRange the dateRange to set
     */
    public void setDateRange(final List<Date> dateRange) {
        this.dateRange = dateRange;
    }

    /**
     * @param employeeEmail the employeeEmail to set
     */
    public void setEmployeeEmail(final String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    /**
     * @param requestType the requestType to set
     */
    public void setRequestType(final Long requestType) {
        this.requestType = requestType;
    }
}
