/******************************************************************************
 * Name : EmployeeDetails.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for EmployeeDetails
 *****************************************************************************/
package com.worldvisa.business.domain.profile;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for EmployeeDetails.
 */
@DataTransferObject(converter = BeanConverter.class)
public class EmployeeRelation
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private String            employeeId;
    private String            managerId;
    private Long              seqNum;

    /**
     * 
     */
    public EmployeeRelation() {
        // default constructor. does nothing
    }

    /**
     * @param managerId
     * @param employeeId
     */
    public EmployeeRelation(final String managerId, final String employeeId) {
        super();
        this.managerId = managerId;
        this.employeeId = employeeId;
    }

    /**
     * @return the employeeId
     */
    public String getEmployeeId() {
        return this.employeeId;
    }

    /**
     * @return the managerId
     */
    public String getManagerId() {
        return this.managerId;
    }

    /**
     * @return the seqNum
     */
    public Long getSeqNum() {
        return this.seqNum;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(final String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @param managerId the managerId to set
     */
    public void setManagerId(final String managerId) {
        this.managerId = managerId;
    }

    /**
     * @param seqNum the seqNum to set
     */
    public void setSeqNum(final Long seqNum) {
        this.seqNum = seqNum;
    }
}
