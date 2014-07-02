/**
 * 
 */
package com.worldvisa.business.views.request;

import java.io.FileInputStream;
import java.util.List;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.io.FileTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import com.worldvisa.business.domain.common.DWRResponse;
import com.worldvisa.business.domain.common.WebMessage;
import com.worldvisa.business.domain.request.ReportDetails;
import com.worldvisa.business.domain.request.RequestManagementFilter;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.infra.constants.ReportType;
import com.worldvisa.business.infra.report.ReportGenerator;
import com.worldvisa.business.service.stub.RequestService;

/**
 * @author Administrator
 */
@RemoteProxy
public class MyAssignmentsView
extends BaseView {
    private static final long serialVersionUID = -637700755115110084L;
    @Autowired
    private ReportGenerator   reportGenerator;
    @Autowired
    private RequestService    requestService;

    @RemoteMethod
    public DWRResponse<String> assign(final String emailId, final List<String> reportIds)
    throws Exception {
        final DWRResponse<String> retVal = new DWRResponse<String>();
        String requests = "";
        retVal.setMainObject(emailId);
        for (final String reportId : reportIds) {
            requests += ", " + reportId;
            this.requestService.updateAssignment(reportId, emailId);
        }
        retVal.setMainObject(emailId);
        retVal.addMessage(new WebMessage("Request(s) " + requests.substring(2) + " assigned successfully", MessageSeverity.INFO));
        return retVal;
    }

    @RemoteMethod
    public FileTransfer exportToExcel(final RequestManagementFilter requestManagementFilter)
    throws Exception {
        requestManagementFilter.setupDefaults();
        // creating and streaming file to server
        return new FileTransfer("ReportDetails.xls", "application/ms-excel", new FileInputStream(this.generateReport(ReportType.XLS,
        "ReportDetails.xls", this.requestService.searchRequests(requestManagementFilter), null, this.reportGenerator)));
    }

    @RemoteMethod
    public List<ReportDetails> getMyAssignmentsList(final RequestManagementFilter requestManagementFilter)
    throws Exception {
        return this.requestService.searchRequests(requestManagementFilter);
    }

    @RemoteMethod
    public int getMyAssignmentsListCount(final RequestManagementFilter requestManagementFilter)
    throws Exception {
        return this.requestService.searchRequestCount(requestManagementFilter);
    }
}
