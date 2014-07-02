/******************************************************************************
 * Name : EmployeePerformanceReport.java Author : Administrator Date : 19-May-2010,8:19:38 PM Description : Implementation for
 * EmployeePerformanceReport
 *****************************************************************************/
package com.worldvisa.business.domain.profile;

import java.util.Date;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * 
 */
@DataTransferObject(converter = BeanConverter.class)
public class EmployeePerformanceReport
extends BaseBean {
    private static final long serialVersionUID = 1081925440352980885L;
    private long              convertedRequests;
    private Date              pdate;
    private long              totalRequest;
    private double            totalRevenue;

    /**
     * @return the convertedRequests
     */
    public long getConvertedRequests() {
        return this.convertedRequests;
    }

    /**
     * @return the pdate
     */
    public Date getPdate() {
        return this.pdate;
    }

    /**
     * @return the totalRequest
     */
    public long getTotalRequest() {
        return this.totalRequest;
    }

    /**
     * @return the totalRevenue
     */
    public double getTotalRevenue() {
        return this.totalRevenue;
    }

    /**
     * @param convertedRequests the convertedRequests to set
     */
    public void setConvertedRequests(final long convertedRequests) {
        this.convertedRequests = convertedRequests;
    }

    /**
     * @param pdate the pdate to set
     */
    public void setPdate(final Date pdate) {
        this.pdate = pdate;
    }

    /**
     * @param totalRequest the totalRequest to set
     */
    public void setTotalRequest(final long totalRequest) {
        this.totalRequest = totalRequest;
    }

    /**
     * @param totalRevenue the totalRevenue to set
     */
    public void setTotalRevenue(final double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
