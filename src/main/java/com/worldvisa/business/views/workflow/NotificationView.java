/**
 * 
 */
package com.worldvisa.business.views.workflow;

import java.io.FileInputStream;
import java.util.List;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.io.FileTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import com.worldvisa.business.domain.common.ContextAwareDataTableRequest;
import com.worldvisa.business.domain.common.DWRResponse;
import com.worldvisa.business.domain.common.WebMessage;
import com.worldvisa.business.domain.workflow.Notification;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.LookupConstants;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.infra.constants.ReportType;
import com.worldvisa.business.infra.report.ReportGenerator;
import com.worldvisa.business.infra.utils.BasicUtils;
import com.worldvisa.business.service.stub.WorkflowService;

/**
 * @author Administrator
 */
@RemoteProxy
public class NotificationView
extends BaseView {
    private static final long serialVersionUID = -637700755115110084L;
    @Autowired
    private ReportGenerator   reportGenerator;
    @Autowired
    private WorkflowService   workflowService;

    @RemoteMethod
    public DWRResponse<Notification> changeNotificationStatus(final Notification currentNotification)
    throws Exception {
        final DWRResponse<Notification> response = new DWRResponse<Notification>();
        currentNotification.setCreationTime(BasicUtils.getCurrentDateTime());
        currentNotification.setByUserId(this.user().getEmailId());
        this.workflowService.updateNotification(currentNotification);
        if (LookupConstants.NOTIFICATION_STATUS.Completed.equals(currentNotification.getNotificationStatus())) {
            response.addMessage(new WebMessage("Notification Acknowledged Successfully", MessageSeverity.INFO));
        } else if (LookupConstants.NOTIFICATION_STATUS.Abandoned.equals(currentNotification.getNotificationStatus())) {
            response.addMessage(new WebMessage("Notification Deleted Successfully", MessageSeverity.INFO));
        } else if (LookupConstants.NOTIFICATION_STATUS.Rescheduled.equals(currentNotification.getNotificationStatus())) {
            response.addMessage(new WebMessage("Notification Rescheduled Successfully", MessageSeverity.INFO));
        }
        response.setMainObject(currentNotification);
        return response;
    }

    @RemoteMethod
    public FileTransfer exportToExcel()
    throws Exception {
        final ContextAwareDataTableRequest dataTableRequest = new ContextAwareDataTableRequest();
        dataTableRequest.setupDefaults();
        dataTableRequest.setEmailId(this.user().getEmailId());
        // creating and streaming file to server
        return new FileTransfer("NotificationDetails.xls", "application/ms-excel", new FileInputStream(this.generateReport(ReportType.XLS,
        "NotificationDetails.xls", this.workflowService.readNotification(dataTableRequest), null, this.reportGenerator)));
    }

    @RemoteMethod
    public List<Notification> getNotificationList(final ContextAwareDataTableRequest dataTableRequest)
    throws Exception {
        dataTableRequest.setEmailId(this.user().getEmailId());
        return this.workflowService.readNotification(dataTableRequest);
    }

    @RemoteMethod
    public int getNotificationListCount(final ContextAwareDataTableRequest dataTableRequest)
    throws Exception {
        dataTableRequest.setEmailId(this.user().getEmailId());
        return this.workflowService.readNotificationListCount(dataTableRequest);
    }

    @RemoteMethod
    public List<Notification> showHistory(final Long NotificationId)
    throws Exception {
        return this.workflowService.readNotificationList(new Notification(NotificationId, null, null));
    }
}
