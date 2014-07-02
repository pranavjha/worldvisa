/**
 * 
 */
package com.worldvisa.business.views.workflow;

import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.io.FileTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import com.worldvisa.business.domain.common.DWRResponse;
import com.worldvisa.business.domain.common.WebMessage;
import com.worldvisa.business.domain.profile.EmployeeDart;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.infra.constants.ReportType;
import com.worldvisa.business.infra.report.ReportGenerator;
import com.worldvisa.business.service.stub.ProfileService;

/**
 * @author Administrator
 */
@RemoteProxy
public class DailyReportingView
extends BaseView {
    private static final long serialVersionUID = 6574225820897485581L;
    @Autowired
    private ProfileService    profileService;
    @Autowired
    private ReportGenerator   reportGenerator;

    @RemoteMethod
    public DWRResponse<EmployeeDart> addDart(final EmployeeDart employeeDart)
    throws Exception {
        final DWRResponse<EmployeeDart> retVal = new DWRResponse<EmployeeDart>();
        retVal.setMainObject(this.profileService.createDart(employeeDart));
        retVal.addMessage(new WebMessage("Activity added successfully.", MessageSeverity.INFO));
        return retVal;
    }

    @RemoteMethod
    public DWRResponse<EmployeeDart> deleteDart(final EmployeeDart employeeDart)
    throws Exception {
        final DWRResponse<EmployeeDart> retVal = new DWRResponse<EmployeeDart>();
        retVal.setMainObject(this.profileService.deleteDart(employeeDart));
        retVal.addMessage(new WebMessage("Activity deleted successfully.", MessageSeverity.INFO));
        return retVal;
    }

    @RemoteMethod
    public FileTransfer exportToExcel(final EmployeeDart filter)
    throws Exception {
        return new FileTransfer("EmployeeDart.xls", "application/ms-excel", new FileInputStream(this.generateReport(ReportType.XLS,
        "EmployeeDart.xls", this.fetchDarts(filter), null, this.reportGenerator)));
    }

    @RemoteMethod
    public List<EmployeeDart> fetchDarts(final EmployeeDart filter)
    throws Exception {
        EmployeeDart newFilter = new EmployeeDart();
        if (filter != null) {
            newFilter = filter;
        }
        if (newFilter.getEmployeeEmail() == null) {
            newFilter.setEmployeeEmail(this.user().getEmailId());
        }
        if (newFilter.getReportDate() == null) {
            newFilter.setReportDate(new Date());
        }
        return this.profileService.readDarts(newFilter);
    }
}
