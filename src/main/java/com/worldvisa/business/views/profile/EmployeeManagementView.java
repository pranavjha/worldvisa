/**
 * 
 */
package com.worldvisa.business.views.profile;

import java.io.FileInputStream;
import java.util.List;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.io.FileTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import com.worldvisa.business.domain.common.DWRResponse;
import com.worldvisa.business.domain.common.WebMessage;
import com.worldvisa.business.domain.profile.EmployeeDetails;
import com.worldvisa.business.domain.profile.EmployeeManagementFilter;
import com.worldvisa.business.domain.workflow.Notification;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.LookupConstants;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.infra.constants.ReportType;
import com.worldvisa.business.infra.report.ReportGenerator;
import com.worldvisa.business.infra.utils.BasicUtils;
import com.worldvisa.business.service.stub.ProfileService;
import com.worldvisa.business.service.stub.WorkflowService;

/**
 * @author Administrator
 */
@RemoteProxy
public class EmployeeManagementView
extends BaseView {
    private static final long serialVersionUID = 5190514015309018428L;
    @Autowired
    private ProfileService    profileService;
    @Autowired
    private ReportGenerator   reportGenerator;
    @Autowired
    private WorkflowService   workflowService;

    @RemoteMethod
    public DWRResponse<Notification> addNotification(final Notification notification)
    throws Exception {
        final DWRResponse<Notification> response = new DWRResponse<Notification>();
        notification.setByUserId(this.user().getEmailId());
        notification.setCreationTime(BasicUtils.getCurrentDateTime());
        notification.setNotificationStatus(LookupConstants.NOTIFICATION_STATUS.Created);
        response.setMainObject(this.workflowService.createNotification(notification));
        response.addMessage(new WebMessage("Notification added successfully", MessageSeverity.INFO));
        return response;
    }

    @RemoteMethod
    public DWRResponse<EmployeeDetails> deleteEmployee(final EmployeeDetails employee)
    throws Exception {
        final DWRResponse<EmployeeDetails> response = new DWRResponse<EmployeeDetails>();
        response.setMainObject(employee);
        this.profileService.deleteEmployee(new EmployeeDetails(employee.getEmployeeId()));
        response.addMessage(new WebMessage("Employee " + employee.getEmployeeName() + " deleted successfully.", MessageSeverity.INFO));
        return response;
    }

    @RemoteMethod
    public FileTransfer exportToExcel(final EmployeeManagementFilter filter)
    throws Exception {
        filter.setupDefaults();
        // creating and streaming file to server
        return new FileTransfer("EmployeeDetails.xls", "application/ms-excel", new FileInputStream(this.generateReport(ReportType.XLS,
        "EmployeeDetails.xls", this.profileService.readEmployeeList(filter), null, this.reportGenerator)));
    }

    @RemoteMethod
    public List<EmployeeDetails> searchResult(final EmployeeManagementFilter filter)
    throws Exception {
        return this.profileService.readEmployeeList(filter);
    }

    @RemoteMethod
    public Integer searchResultCount(final EmployeeManagementFilter filter)
    throws Exception {
        return this.profileService.readEmployeeListLength(filter);
    }
}
