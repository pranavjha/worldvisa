/******************************************************************************
 * Name : RequestServiceImpl.java Author : Administrator Date : 09-May-2010,2:28:06 AM Description : Implementation for RequestServiceImpl
 *****************************************************************************/
package com.worldvisa.business.service.stub;

import java.util.List;
import com.worldvisa.business.domain.common.DataTableRequest;
import com.worldvisa.business.domain.profile.LoginDetails;
import com.worldvisa.business.domain.request.AttributeMaster;
import com.worldvisa.business.domain.request.CaseStatus;
import com.worldvisa.business.domain.request.ReportDetails;
import com.worldvisa.business.domain.request.RequestManagementFilter;

/**
 * 
 */
public interface RequestService {
    public CaseStatus createCaseStatus(LoginDetails details, CaseStatus caseStatus)
    throws Exception;

    public List<CaseStatus> readCaseStatus(String reportId)
    throws Exception;

    public List<AttributeMaster> readReportMasterAttributes(Long reportType)
    throws Exception;

    public ReportDetails readRequest(ReportDetails reportDetails)
    throws Exception;

    public ReportDetails readRequestDetails(String reportId)
    throws Exception;

    public ReportDetails saveRequest(LoginDetails loginDetails, ReportDetails reportDetails)
    throws Exception;

    public int searchRequestCount(RequestManagementFilter filter)
    throws Exception;

    public List<ReportDetails> searchRequests(RequestManagementFilter filter)
    throws Exception;

    public int searchUnassignedRequestCount(DataTableRequest request)
    throws Exception;

    public List<ReportDetails> searchUnassignedRequests(DataTableRequest request)
    throws Exception;

    public void updateAssignment(String requestId, String emailId)
    throws Exception;

    public ReportDetails updateRequest(ReportDetails details, String oldEmailId, LoginDetails loginDetails)
    throws Exception;

    public boolean validateEmail(String emailId)
    throws Exception;
}
