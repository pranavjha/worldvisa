/******************************************************************************
 * Name : ProfileServiceImpl.java Author : Administrator Date : 09-May-2010,2:27:56 AM Description : Implementation for ProfileServiceImpl
 *****************************************************************************/
package com.worldvisa.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.worldvisa.business.dao.profile.EmployeeDartDAO;
import com.worldvisa.business.dao.profile.EmployeeDetailsDAO;
import com.worldvisa.business.dao.profile.EmployeeRelationDAO;
import com.worldvisa.business.dao.profile.LoginDetailsDAO;
import com.worldvisa.business.domain.profile.EmployeeDart;
import com.worldvisa.business.domain.profile.EmployeeDetails;
import com.worldvisa.business.domain.profile.EmployeeManagementFilter;
import com.worldvisa.business.domain.profile.EmployeePerformanceFilter;
import com.worldvisa.business.domain.profile.EmployeePerformanceReport;
import com.worldvisa.business.domain.profile.EmployeeRelation;
import com.worldvisa.business.domain.profile.LoginDetails;
import com.worldvisa.business.domain.request.ReportDetails;
import com.worldvisa.business.domain.request.RequestManagementFilter;
import com.worldvisa.business.infra.base.BaseServiceImpl;
import com.worldvisa.business.infra.constants.LookupConstants;
import com.worldvisa.business.infra.utils.BasicUtils;
import com.worldvisa.business.service.stub.ProfileService;
import com.worldvisa.business.service.stub.RequestService;

/**
 * 
 */
@Service
public class ProfileServiceImpl
extends BaseServiceImpl
implements ProfileService {
    @Autowired
    private EmployeeDartDAO     employeeDartDAO;
    @Autowired
    private EmployeeDetailsDAO  employeeDetailsDAO;
    @Autowired
    private EmployeeRelationDAO employeeRelationDAO;
    @Autowired
    private LoginDetailsDAO     loginDetailsDAO;
    @Autowired
    private RequestService      requestService;

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#createDart(com.worldvisa.business.domain.profile.EmployeeDart)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public EmployeeDart createDart(final EmployeeDart employeeDart)
    throws Exception {
        try {
            this.employeeDartDAO.create(employeeDart);
        } catch (final Exception e) {
            final Exception ex = new Exception("Dart details could not be added.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        return employeeDart;
    }

    public EmployeeDart createPublicDart(LoginDetails user, String description, Long dartType, String reportId)
    throws Exception {
        final EmployeeDart dart = new EmployeeDart();
        dart.setDartType(dartType);
        dart.setDescription(description);
        dart.setEmployeeEmail(user.getEmailId());
        dart.setReportDate(BasicUtils.getCurrentDateTime());
        dart.setReportId(reportId);
        this.employeeDartDAO.create(dart);
        return dart;
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#createUser(com.worldvisa.business.domain.profile.EmployeeDetails)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public EmployeeDetails createUser(final LoginDetails user, final EmployeeDetails userDetails)
    throws Exception {
        userDetails.setCreatedBy(user.getEmailId());
        userDetails.setCreatedDate(BasicUtils.getCurrentDateTime());
        userDetails.setIsActive(LookupConstants.INDICES.TRUE);
        userDetails.setPassword("" + ((int) (Math.random() * 10000 + 10000)));
        userDetails.setPasswordExpFlag(LookupConstants.INDICES.TRUE);
        userDetails.setRegistrationStatus(LookupConstants.CLIENT_REG_STATUS.Registerd);
        try {
            this.loginDetailsDAO.create(new LoginDetails(userDetails));
            userDetails.setEmployeeId((Long) this.employeeDetailsDAO.create(userDetails));
            if (userDetails.getReportingManagers() != null) {
                for (final String manager : userDetails.getReportingManagers()) {
                    if (manager != null && manager.trim().length() != 0) {
                        this.employeeRelationDAO.create(new EmployeeRelation(manager, userDetails.getEmailId()));
                    }
                }
            }
        } catch (final Exception e) {
            final Exception ex = new Exception(
            "The email id entered is already registered in the worldvisa system. Please choose a different email Id for correspondence.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        return userDetails;
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#deleteDart(com.worldvisa.business.domain.profile.EmployeeDart)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public EmployeeDart deleteDart(final EmployeeDart employeeDart)
    throws Exception {
        try {
            this.employeeDartDAO.delete(new EmployeeDart(employeeDart.getEmployeeEmail(), employeeDart.getReportDate(), employeeDart.getSeqNum()));
        } catch (final Exception e) {
            final Exception ex = new Exception("Dart details could not be deleted.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        return employeeDart;
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#readEmployeeList(com.worldvisa.business.domain.profile.EmployeeManagementFilter)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public int deleteEmployee(EmployeeDetails employeeDetails)
    throws Exception {
        employeeDetails = this.employeeDetailsDAO.read(employeeDetails);
        final RequestManagementFilter reportDetailsFilter = new RequestManagementFilter();
        reportDetailsFilter.setupDefaults();
        String reportList = "";
        reportDetailsFilter.setAssignedTo(employeeDetails.getEmailId());
        final List<ReportDetails> reportsList = this.requestService.searchRequests(reportDetailsFilter);
        BaseServiceImpl.log.debug(employeeDetails.getEmailId() + " has " + reportsList.size() + " requests assigned to it");
        for (final ReportDetails details : reportsList) {
            if (!LookupConstants.CASE_SUBSTATUS.RequestAbandoned.equals(details.getCaseStatus())
            && !LookupConstants.CASE_SUBSTATUS.RequestCompleted.equals(details.getCaseStatus())) {
                reportList += (", " + details.getReportId());
            }
        }
        if (reportList.length() > 0) {
            reportList = reportList.substring(2);
            throw new Exception(employeeDetails.getEmployeeName() + " has the following incomplete requests assigned to him: " + reportList
            + ". Please reassign/move these requests into basket before proceeding.");
        }
        final LoginDetails filter = new LoginDetails(employeeDetails.getEmailId());
        try {
            this.employeeDartDAO.delete(new EmployeeDart(employeeDetails.getEmailId(), null, null));
            this.employeeRelationDAO.delete(new EmployeeRelation(null, employeeDetails.getEmailId()));
            this.employeeRelationDAO.delete(new EmployeeRelation(employeeDetails.getEmailId(), null));
            this.loginDetailsDAO.delete(filter);
        } catch (final Exception e) {
            final Exception ex = new Exception("Employee Profile cannot be deleted. Please contact administrator for assistance");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        return this.employeeDetailsDAO.delete(new EmployeeDetails(employeeDetails.getEmployeeId()));
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#readAllEmployee()
     */
    public List<EmployeeDetails> readAllEmployee()
    throws Exception {
        return this.employeeDetailsDAO.readList(new EmployeeDetails());
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#getAllManagersAsSelectItems()
     */
    public List<LoginDetails> readAllManagers()
    throws Exception {
        final LoginDetails loginDetails = new LoginDetails();
        loginDetails.setUserRole(LookupConstants.USER_ROLE.Manager);
        return this.loginDetailsDAO.readList(loginDetails);
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#readAssignmentOptions(java.lang.String)
     */
    public List<EmployeeDetails> readAssignmentOptions(final String emailId)
    throws Exception {
        return this.employeeDetailsDAO.readAssignmentOptions(emailId);
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#readDarts(com.worldvisa.business.domain.profile.EmployeeDart)
     */
    public List<EmployeeDart> readDarts(final EmployeeDart employeeDart)
    throws Exception {
        try {
            return this.employeeDartDAO.readList(employeeDart);
        } catch (final Exception e) {
            final Exception ex = new Exception(
            "The system behaved unexpectedly and could not generate the performance report. Try refreshing the page. If the problem persists, contact administrator for assistance.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#readEmployee(com.worldvisa.business.domain.profile.EmployeeDetails)
     */
    public EmployeeDetails readEmployee(final EmployeeDetails employeeDetails)
    throws Exception {
        final EmployeeDetails details = this.employeeDetailsDAO.read(employeeDetails);
        final List<EmployeeRelation> relations = this.employeeRelationDAO.readList(new EmployeeRelation(null, details.getEmailId()));
        details.setReportingManagers(new ArrayList<String>());
        if (relations != null && relations.size() != 0) {
            for (final EmployeeRelation relation : relations) {
                details.getReportingManagers().add(relation.getManagerId());
            }
        }
        return details;
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#readEmployeeList(com.worldvisa.business.domain.profile.EmployeeManagementFilter)
     */
    public List<EmployeeDetails> readEmployeeList(final EmployeeManagementFilter filter)
    throws Exception {
        return this.employeeDetailsDAO.readListByFilter(filter);
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#readEmployeeListLength(com.worldvisa.business.domain.profile.EmployeeManagementFilter)
     */
    public Integer readEmployeeListLength(final EmployeeManagementFilter filter)
    throws Exception {
        return this.employeeDetailsDAO.readListLengthByFilter(filter);
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#validateCredentials(com.worldvisa.business.domain.profile.LoginDetails)
     */
    public LoginDetails readLoginDetails(final LoginDetails loginDetails)
    throws Exception {
        final LoginDetails details = this.loginDetailsDAO.read(new LoginDetails(loginDetails.getEmailId()));
        return details;
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#readPerformanceReport(com.worldvisa.business.domain.profile.EmployeePerformanceFilter)
     */
    public List<EmployeePerformanceReport> readPerformanceReport(final EmployeePerformanceFilter filter)
    throws Exception {
        return this.employeeDetailsDAO.getPerformanceReport(filter);
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#updateEmployee(com.worldvisa.business.domain.profile.EmployeeDetails)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Integer updateEmployee(final EmployeeDetails employeeDetails)
    throws Exception {
        final Integer rows = this.employeeDetailsDAO.update(employeeDetails, new EmployeeDetails(employeeDetails.getEmployeeId()));
        try {
            this.loginDetailsDAO.update(new LoginDetails(employeeDetails), new LoginDetails(employeeDetails.getEmailId()));
            this.employeeRelationDAO.delete(new EmployeeRelation(null, employeeDetails.getEmailId()));
            if (employeeDetails.getReportingManagers() != null) {
                for (final String manager : employeeDetails.getReportingManagers()) {
                    if (manager != null && manager.trim().length() != 0) {
                        this.employeeRelationDAO.create(new EmployeeRelation(manager, employeeDetails.getEmailId()));
                    }
                }
            }
        } catch (final Exception e) {
            final Exception ex = new Exception(
            "Profile could be updated. Please check if you have furnished all details. If the problem persists, contact administrator for assistance.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        return rows;
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#updatePassword(com.worldvisa.business.domain.profile.LoginDetails, java.lang.String)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public LoginDetails updatePassword(final LoginDetails user, final String newPassword)
    throws Exception {
        final LoginDetails data = new LoginDetails(user);
        data.setPassword(newPassword);
        data.setPasswordExpFlag(LookupConstants.INDICES.FALSE);
        final LoginDetails filter = new LoginDetails(user.getEmailId());
        try {
            this.loginDetailsDAO.update(data, filter);
        } catch (final Exception e) {
            final Exception ex = new Exception(
            "Password could not be changed. Please check if you have filled all required fields. If the problem persists, contact administrator for assistance.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        return data;
    }
}