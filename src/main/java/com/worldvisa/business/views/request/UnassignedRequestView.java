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
import com.worldvisa.business.domain.common.DataTableRequest;
import com.worldvisa.business.domain.common.WebMessage;
import com.worldvisa.business.domain.request.ReportDetails;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.infra.constants.ReportType;
import com.worldvisa.business.infra.report.ReportGenerator;
import com.worldvisa.business.service.stub.RequestService;

/**
 * @author Administrator
 */
@RemoteProxy
public class UnassignedRequestView
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
    public FileTransfer exportToExcel()
    throws Exception {
        final DataTableRequest request = new DataTableRequest().setupDefaults();
        // creating and streaming file to server
        return new FileTransfer("UnassignedRequests.xls", "application/ms-excel", new FileInputStream(this.generateReport(ReportType.XLS,
        "ReportDetails.xls", this.requestService.searchUnassignedRequests(request), null, this.reportGenerator)));
    }

    @RemoteMethod
    public List<ReportDetails> getUnassignedRequestList(final DataTableRequest request)
    throws Exception {
        return this.requestService.searchUnassignedRequests(request);
    }

    @RemoteMethod
    public int getUnassignedRequestListCount(final DataTableRequest request)
    throws Exception {
        return this.requestService.searchUnassignedRequestCount(request);
    }
}
