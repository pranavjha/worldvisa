/******************************************************************************
 * Name : ReportDetailsDAO.java Author : Administrator Date : 2010/05/08 Description : Implementation for the ReportDetailsDAO interface
 *****************************************************************************/
package com.worldvisa.business.dao.request;

import java.util.List;
import com.worldvisa.business.domain.common.DataTableRequest;
import com.worldvisa.business.domain.request.ReportDetails;
import com.worldvisa.business.domain.request.RequestManagementFilter;
import com.worldvisa.business.infra.base.BaseIbatisDAO;

/**
 * DAO interface for interfacing with the REQUEST_REPORT_DETAILS table<br/>
 */
public interface ReportDetailsDAO
extends BaseIbatisDAO<ReportDetails> {
    public String getNextReportId(String prefix)
    throws Exception;

    public List<ReportDetails> readRequests(RequestManagementFilter requestManagementFilter)
    throws Exception;

    public int readRequestsCount(RequestManagementFilter requestManagementFilter)
    throws Exception;

    public List<ReportDetails> readUnassignedRequest(DataTableRequest request)
    throws Exception;

    public int readUnassignedRequestCount(DataTableRequest request)
    throws Exception;
}
