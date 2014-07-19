/******************************************************************************
 * Name : ReportDetailsDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the ReportDetailsDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.request;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.request.ReportDetailsDAO;
import com.worldvisa.business.domain.common.DataTableRequest;
import com.worldvisa.business.domain.request.ReportDetails;
import com.worldvisa.business.domain.request.RequestManagementFilter;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.REQUEST_REPORT_DETAILS table<br/>
 */
@Repository("requestReportDetailsDAOImpl")
public class ReportDetailsDAOImpl
extends BaseIbatisDAOImpl<ReportDetails>
implements ReportDetailsDAO {
    /**
     * <one character representing service type> <1 character country code> <1 character representing location> <DOB in YYYYMMDD format> <sl number 3
     * digits>
     * @see com.worldvisa.business.dao.request.ReportDetailsDAO#getNextReportId()
     */
    public String getNextReportId(final String prefix)
    throws Exception {
        String nextReportId = (String) this.getSqlMapClientTemplate().queryForObject("requestReportDetails.lastReportId", prefix);
        if (nextReportId == null) {
            nextReportId = prefix + "001";
        } else {
            int iPrefix = 1000 + Integer.parseInt(nextReportId.substring(nextReportId.length() - 3));
            iPrefix++;
            nextReportId = prefix + ("" + iPrefix).substring(1);
        }
        return nextReportId;
    }

    /**
     * @see com.worldvisa.business.dao.request.ReportDetailsDAO#readRequests(com.worldvisa.business.domain.request.RequestManagementFilter)
     */
    @SuppressWarnings("unchecked")
    public List<ReportDetails> readRequests(final RequestManagementFilter requestManagementFilter)
    throws Exception {
        return this.getSqlMapClientTemplate().queryForList("requestReportDetails.allRequests", requestManagementFilter);
    }

    public int readRequestsCount(final RequestManagementFilter requestManagementFilter)
    throws Exception {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("requestReportDetails.allRequestsCount", requestManagementFilter);
    }

    @SuppressWarnings("unchecked")
    public List<ReportDetails> readUnassignedRequest(final DataTableRequest request)
    throws Exception {
        return this.getSqlMapClientTemplate().queryForList("requestReportDetails.unassignedRequest", request);
    }

    public int readUnassignedRequestCount(final DataTableRequest request)
    throws Exception {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("requestReportDetails.unassignedRequestCount", request);
    }
}
