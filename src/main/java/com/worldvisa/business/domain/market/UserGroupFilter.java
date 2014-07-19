/******************************************************************************
 * Name : UserDetails.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for UserDetails
 *****************************************************************************/
package com.worldvisa.business.domain.market;

import java.util.Date;
import java.util.List;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.domain.common.DataTableRequest;

/**
 * POJO implementation for UserDetails.
 */
@DataTransferObject(converter = BeanConverter.class)
public class UserGroupFilter
extends DataTableRequest {
    private static final long serialVersionUID = -7738471592883206120L;
    private String            addedBy;
    private List<Date>        addedOn;
    private Long              branchOffice;
    private Long              serviceType;
    private String            userGroupName;

    /**
     * @return the addedBy
     */
    public String getAddedBy() {
        return this.addedBy;
    }

    /**
     * @return the addedOn
     */
    public List<Date> getAddedOn() {
        return this.addedOn;
    }

    /**
     * @return the branchOffice
     */
    public Long getBranchOffice() {
        return this.branchOffice;
    }

    /**
     * @return the serviceType
     */
    public Long getServiceType() {
        return this.serviceType;
    }

    /**
     * @return the userGroupName
     */
    public String getUserGroupName() {
        return this.userGroupName;
    }

    /**
     * @param addedBy the addedBy to set
     */
    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    /**
     * @param addedOn the addedOn to set
     */
    public void setAddedOn(List<Date> addedOn) {
        this.addedOn = addedOn;
    }

    /**
     * @param branchOffice the branchOffice to set
     */
    public void setBranchOffice(Long branchOffice) {
        this.branchOffice = branchOffice;
    }

    /**
     * @param serviceType the serviceType to set
     */
    public void setServiceType(Long serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * @param userGroupName the userGroupName to set
     */
    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }
}