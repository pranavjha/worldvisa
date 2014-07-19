/******************************************************************************
 * Name : EmployeeDetailsDAO.java Author : Administrator Date : 2010/05/08 Description : Implementation for the EmployeeDetailsDAO interface
 *****************************************************************************/
package com.worldvisa.business.dao.profile;

import java.util.List;
import com.worldvisa.business.domain.profile.EmployeeDetails;
import com.worldvisa.business.domain.profile.EmployeeManagementFilter;
import com.worldvisa.business.domain.profile.EmployeePerformanceFilter;
import com.worldvisa.business.domain.profile.EmployeePerformanceReport;
import com.worldvisa.business.infra.base.BaseIbatisDAO;

/**
 * DAO interface for interfacing with the PROFILE_EMPLOYEE_DETAILS table<br/>
 */
public interface EmployeeDetailsDAO
extends BaseIbatisDAO<EmployeeDetails> {
    /**
     * @param filter
     * @return
     */
    public List<EmployeePerformanceReport> getPerformanceReport(EmployeePerformanceFilter filter)
    throws Exception;

    /**
     * @param emailId
     * @return
     */
    public List<EmployeeDetails> readAssignmentOptions(String emailId)
    throws Exception;

    /**
     * @param filter
     * @return
     */
    List<EmployeeDetails> readListByFilter(EmployeeManagementFilter filter)
    throws Exception;

    /**
     * @param filter
     * @return
     */
    public Integer readListLengthByFilter(EmployeeManagementFilter filter)
    throws Exception;
}
