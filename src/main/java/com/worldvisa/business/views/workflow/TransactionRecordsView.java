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
import com.worldvisa.business.domain.workflow.Payment;
import com.worldvisa.business.domain.workflow.TransactionRecordsFilter;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.ReportType;
import com.worldvisa.business.infra.report.ReportGenerator;
import com.worldvisa.business.service.stub.RequestService;
import com.worldvisa.business.service.stub.WorkflowService;

/**
 * @author Administrator
 */
@RemoteProxy
public class TransactionRecordsView
extends BaseView {
    private static final long serialVersionUID = 3439391191714102604L;
    @Autowired
    private ReportGenerator   reportGenerator;
    @Autowired
    private RequestService    requestService;
    @Autowired
    private WorkflowService   workflowService;

    @RemoteMethod
    public FileTransfer downloadAllReceipts(final TransactionRecordsFilter filter)
    throws Exception {
        filter.setupDefaults();
        final List<Payment> paymentList = this.readSearchList(filter);
        for (final Payment payment : paymentList) {
            payment.setDetails(this.requestService.readRequestDetails(payment.getReportId()));
        }
        return new FileTransfer("PaymentInvoices.pdf", "application/pdf", new FileInputStream(this.generateReport(ReportType.PDF,
        "PaymentInvoice.jrxml", null, paymentList, this.reportGenerator)));
    }

    @RemoteMethod
    public FileTransfer downloadReceipt(final String paymentId)
    throws Exception {
        final List<Payment> paymentList = this.workflowService.readPaymentList(new Payment(paymentId, null));
        paymentList.get(0).setDetails(this.requestService.readRequestDetails(paymentList.get(0).getReportId()));
        return new FileTransfer("PaymentInvoices.pdf", "application/pdf", new FileInputStream(this.generateReport(ReportType.PDF,
        "PaymentInvoice.jrxml", null, paymentList, this.reportGenerator)));
    }

    @RemoteMethod
    public FileTransfer exportToExcel(final TransactionRecordsFilter filter)
    throws Exception {
        filter.setupDefaults();
        // creating and streaming file to server
        return new FileTransfer("TransactionDetails.xls", "application/ms-excel", new FileInputStream(this.generateReport(ReportType.XLS,
        "TransactionDetails.xls", this.readSearchList(filter), null, this.reportGenerator)));
    }

    @RemoteMethod
    public List<Payment> readSearchList(final TransactionRecordsFilter filter)
    throws Exception {
        return this.workflowService.readPaymentRecords(filter);
    }

    @RemoteMethod
    public Integer readSearchListCount(final TransactionRecordsFilter filter)
    throws Exception {
        return this.workflowService.readPaymentRecordsCount(filter);
    }
}
