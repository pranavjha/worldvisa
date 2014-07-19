/******************************************************************************
 * Name : RequestManagementFilter.java Author : Administrator Date : 17-May-2010,10:39:25 PM Description : Implementation for RequestManagementFilter
 *****************************************************************************/
package com.worldvisa.business.domain.request;

import java.util.Date;
import java.util.List;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.domain.common.DataTableRequest;

/**
 * 
 */
@DataTransferObject(converter = BeanConverter.class)
public class RequestManagementFilter
extends DataTableRequest {
    private static final long serialVersionUID = 1614161022378932912L;
    private String            assignedTo;
    private Long              caseSubStatus;
    private String            clientEmail;
    private String            clientName;
    private Long              clientStatus;
    private List<Date>        dateRange;
    private String            mobile;
    private Long              mode;
    private String            reportId;
    private Long              requestType;
    private Long              slaStatus;

    /**
     * @return the assignedTo
     */
    public String getAssignedTo() {
        return this.assignedTo;
    }

    /**
     * @return the caseSubStatus
     */
    public Long getCaseSubStatus() {
        return this.caseSubStatus;
    }

    /**
     * @return the clientEmail
     */
    public String getClientEmail() {
        return this.clientEmail;
    }

    /**
     * @return the clientName
     */
    public String getClientName() {
        return this.clientName;
    }

    /**
     * @return the clientStatus
     */
    public Long getClientStatus() {
        return this.clientStatus;
    }

    /**
     * @return the dateRangeFrom
     */
    public List<Date> getDateRange() {
        return this.dateRange;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * @return the mode
     */
    public Long getMode() {
        return this.mode;
    }

    /**
     * @return the reportId
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * @return the requestType
     */
    public Long getRequestType() {
        return this.requestType;
    }

    /**
     * @return the slaStatus
     */
    public Long getSlaStatus() {
        return this.slaStatus;
    }

    /**
     * @param assignedTo the assignedTo to set
     */
    public void setAssignedTo(final String assignedTo) {
        if (null == assignedTo || assignedTo.trim().length() == 0) {
            this.assignedTo = null;
            return;
        }
        this.assignedTo = assignedTo;
    }

    /**
     * @param caseSubStatus the caseSubStatus to set
     */
    public void setCaseSubStatus(final Long caseSubStatus) {
        this.caseSubStatus = caseSubStatus;
    }

    /**
     * @param clientEmail the clientEmail to set
     */
    public void setClientEmail(final String clientEmail) {
        this.clientEmail = clientEmail;
    }

    /**
     * @param clientName the clientName to set
     */
    public void setClientName(final String clientName) {
        this.clientName = clientName;
    }

    /**
     * @param clientStatus the clientStatus to set
     */
    public void setClientStatus(final Long clientStatus) {
        this.clientStatus = clientStatus;
    }

    /**
     * @param dateRangeFrom the dateRangeFrom to set
     */
    public void setDateRange(final List<Date> dateRange) {
        this.dateRange = dateRange;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(final Long mode) {
        this.mode = mode;
    }

    /**
     * @param reportId the reportId to set
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    /**
     * @param requestType the requestType to set
     */
    public void setRequestType(final Long requestType) {
        this.requestType = requestType;
    }

    /**
     * @param slaStatus the slaStatus to set
     */
    public void setSlaStatus(final Long slaStatus) {
        this.slaStatus = slaStatus;
    }
}
