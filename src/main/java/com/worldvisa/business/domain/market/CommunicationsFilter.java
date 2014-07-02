/******************************************************************************
 * Name : RequestManagementFilter.java Author : Administrator Date : 17-May-2010,10:39:25 PM Description : Implementation for RequestManagementFilter
 *****************************************************************************/
package com.worldvisa.business.domain.market;

import java.util.Date;
import java.util.List;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.domain.common.DataTableRequest;

/**
 * 
 */
@DataTransferObject(converter = BeanConverter.class)
public class CommunicationsFilter
extends DataTableRequest {
    private static final long serialVersionUID = 1614161022378932912L;
    private Long              actionType;
    private String            addedBy;
    private List<Date>        dateRange;
    private String            mailSubject;

    /**
     * @return the actionType
     */
    public Long getActionType() {
        return this.actionType;
    }

    /**
     * @return the addedBy
     */
    public String getAddedBy() {
        return this.addedBy;
    }

    /**
     * @return the dateRange
     */
    public List<Date> getDateRange() {
        return this.dateRange;
    }

    /**
     * @return the mailSubject
     */
    public String getMailSubject() {
        return this.mailSubject;
    }

    /**
     * @param actionType the actionType to set
     */
    public void setActionType(final Long actionType) {
        this.actionType = actionType;
    }

    /**
     * @param addedBy the addedBy to set
     */
    public void setAddedBy(final String addedBy) {
        this.addedBy = addedBy;
    }

    /**
     * @param dateRange the dateRange to set
     */
    public void setDateRange(final List<Date> dateRange) {
        this.dateRange = dateRange;
    }

    /**
     * @param mailSubject the mailSubject to set
     */
    public void setMailSubject(final String mailSubject) {
        this.mailSubject = mailSubject;
    }
}
