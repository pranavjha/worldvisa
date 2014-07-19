/******************************************************************************
 * Name : ProfileServiceImpl.java Author : Administrator Date : 09-May-2010,2:27:56 AM Description : Implementation for ProfileServiceImpl
 *****************************************************************************/
package com.worldvisa.business.service.stub;

import java.util.List;
import com.worldvisa.business.domain.profile.EmployeeDart;
import com.worldvisa.business.domain.profile.EmployeeDetails;
import com.worldvisa.business.domain.profile.EmployeeManagementFilter;
import com.worldvisa.business.domain.profile.EmployeePerformanceFilter;
import com.worldvisa.business.domain.profile.EmployeePerformanceReport;
import com.worldvisa.business.domain.profile.LoginDetails;

/**
 * 
 */
public interface ProfileService {
    /**
     * @param employeeDart
     * @return
     */
    public EmployeeDart createDart(EmployeeDart employeeDart)
    throws Exception;

    /**
     * @param employeeDart
     * @return
     */
    public EmployeeDart createPublicDart(LoginDetails user, String description, Long dartType, String reportId)
    throws Exception;

    /**
     * @param userDetails
     * @return
     * @throws Exception
     */
    public EmployeeDetails createUser(LoginDetails user, EmployeeDetails userDetails)
    throws Exception;

    /**
     * @param employeeDart
     * @return
     */
    public EmployeeDart deleteDart(EmployeeDart employeeDart)
    throws Exception;

    /**
     * @param employeeDetails
     * @return
     * @throws Exception
     */
    int deleteEmployee(EmployeeDetails employeeDetails)
    throws Exception;

    /**
     * @return
     */
    public List<EmployeeDetails> readAllEmployee()
    throws Exception;

    /**
     * @return
     */
    public List<LoginDetails> readAllManagers()
    throws Exception;

    /**
     * @param emailId
     * @return
     */
    public List<EmployeeDetails> readAssignmentOptions(String emailId)
    throws Exception;

    /**
     * @param employeeDart
     * @return
     */
    public List<EmployeeDart> readDarts(EmployeeDart employeeDart)
    throws Exception;

    /**
     * @param employeeDetails
     * @return
     * @throws Exception
     */
    EmployeeDetails readEmployee(EmployeeDetails employeeDetails)
    throws Exception;

    /**
     * @param filter
     * @return
     */
    List<EmployeeDetails> readEmployeeList(EmployeeManagementFilter filter)
    throws Exception;

    /**
     * @param filter
     * @return
     */
    public Integer readEmployeeListLength(EmployeeManagementFilter filter)
    throws Exception;

    /**
     * Validates the login credentials for a user.
     * @param loginDetails the credentials supplied
     * @return null if the credentials are invalid. Populated loginDetails object otherwise
     * @throws Exception in case of an error
     */
    public LoginDetails readLoginDetails(LoginDetails loginDetails)
    throws Exception;

    /**
     * @param filter
     * @return
     * @throws Exception
     */
    List<EmployeePerformanceReport> readPerformanceReport(EmployeePerformanceFilter filter)
    throws Exception;

    /**
     * @param employeeDetails
     * @return
     * @throws Exception
     */
    Integer updateEmployee(EmployeeDetails employeeDetails)
    throws Exception;

    /**
     * @param user
     * @param newPassword
     */
    public LoginDetails updatePassword(LoginDetails user, String newPassword)
    throws Exception;
}
