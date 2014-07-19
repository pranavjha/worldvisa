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
import com.worldvisa.business.domain.profile.EmployeePerformanceFilter;
import com.worldvisa.business.domain.profile.EmployeePerformanceReport;
import com.worldvisa.business.domain.profile.LoginDetails;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.infra.constants.ReportType;
import com.worldvisa.business.infra.report.ReportGenerator;
import com.worldvisa.business.infra.utils.Mailer;
import com.worldvisa.business.service.stub.ProfileService;

/**
 * @author Administrator
 */
@RemoteProxy
public class EmployeeDetailsView
extends BaseView {
    private static final long serialVersionUID = -8739278270863375864L;
    @Autowired
    private Mailer            mailer;
    @Autowired
    private ProfileService    profileService;
    @Autowired
    private ReportGenerator   reportGenerator;

    @RemoteMethod
    public DWRResponse<EmployeeDetails> createEmployee(final EmployeeDetails employeeDetails)
    throws Exception {
        final DWRResponse<EmployeeDetails> response = new DWRResponse<EmployeeDetails>();
        response.setMainObject(this.profileService.createUser(this.user(), employeeDetails));
        this.mailer.sendEmail(response.getMainObject(), "Your profile has been created in worldVisa", "EmployeeLoginActivated.html", response
        .getMainObject().getEmailId());
        response.addMessage(new WebMessage(response.getMainObject().getEmployeeName()
        + " is now registered successfully. An email has been sent to his email id with the credentials.", MessageSeverity.INFO));
        return response;
    }

    @RemoteMethod
    public FileTransfer exportToExcel(final EmployeePerformanceFilter filter)
    throws Exception {
        return new FileTransfer("PerformanceDetails-" + filter.getEmployeeEmail() + ".xls", "application/ms-excel", new FileInputStream(
        this.generateReport(ReportType.XLS, "PerformanceDetails.xls", this.profileService.readPerformanceReport(filter), null, this.reportGenerator)));
    }

    @RemoteMethod
    public DWRResponse<EmployeeDetails> getEmployeeDetails(final EmployeeDetails employeeDetails)
    throws Exception {
        final DWRResponse<EmployeeDetails> response = new DWRResponse<EmployeeDetails>();
        response.setMainObject(this.profileService.readEmployee(new EmployeeDetails(employeeDetails.getEmployeeId())));
        return response;
    }

    @RemoteMethod
    public List<EmployeePerformanceReport> getPerformanceReport(final EmployeePerformanceFilter filter)
    throws Exception {
        return this.profileService.readPerformanceReport(filter);
    }

    @RemoteMethod
    public List<LoginDetails> getReportsTo()
    throws Exception {
        return this.profileService.readAllManagers();
    }

    @RemoteMethod
    public DWRResponse<EmployeeDetails> save(final EmployeeDetails employeeDetails)
    throws Exception {
        final DWRResponse<EmployeeDetails> response = new DWRResponse<EmployeeDetails>();
        response.setMainObject(employeeDetails);
        this.profileService.updateEmployee(employeeDetails);
        response.addMessage(new WebMessage("Employee Details updated successfully", MessageSeverity.INFO));
        return response;
    }
}
