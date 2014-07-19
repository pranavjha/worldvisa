/**
 * 
 */
package com.worldvisa.business.views.request;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.io.FileTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import com.worldvisa.business.domain.common.DWRResponse;
import com.worldvisa.business.domain.common.FileBean;
import com.worldvisa.business.domain.common.WebMessage;
import com.worldvisa.business.domain.profile.LoginDetails;
import com.worldvisa.business.domain.request.AttributeMaster;
import com.worldvisa.business.domain.request.CaseStatus;
import com.worldvisa.business.domain.request.ReportDetails;
import com.worldvisa.business.domain.workflow.Conversation;
import com.worldvisa.business.domain.workflow.FileDetails;
import com.worldvisa.business.domain.workflow.Notification;
import com.worldvisa.business.domain.workflow.Payment;
import com.worldvisa.business.domain.workflow.PaymentOption;
import com.worldvisa.business.infra.annotations.AuthenticationBypass;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.LookupConstants;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.infra.constants.ReportType;
import com.worldvisa.business.infra.exception.AuthorizationException;
import com.worldvisa.business.infra.report.ReportGenerator;
import com.worldvisa.business.infra.utils.BasicUtils;
import com.worldvisa.business.infra.utils.Mailer;
import com.worldvisa.business.service.stub.CommonService;
import com.worldvisa.business.service.stub.ProfileService;
import com.worldvisa.business.service.stub.RequestService;
import com.worldvisa.business.service.stub.WorkflowService;

/**
 * @author Administrator
 */
@RemoteProxy
public class ReportDetailsView
extends BaseView {
    private static final long serialVersionUID = -2286811019279958842L;
    @Autowired
    private CommonService     commonService;
    @Autowired
    private Mailer            mailer;
    @Autowired
    private ProfileService    profileService;
    @Autowired
    private ReportGenerator   reportGenerator;
    @Autowired
    private RequestService    requestService;
    @Autowired
    private WorkflowService   workflowService;

    @RemoteMethod
    public DWRResponse<CaseStatus> addCaseStatus(final CaseStatus caseStatus)
    throws Exception {
        final DWRResponse<CaseStatus> response = new DWRResponse<CaseStatus>();
        caseStatus.setAddedDate(BasicUtils.getCurrentDateTime());
        caseStatus.setSetBy(this.user().getEmailId());
        response.setMainObject(caseStatus);
        this.requestService.createCaseStatus(this.user(), caseStatus);
        response.addMessage(new WebMessage("Case status updated successfully.", MessageSeverity.INFO));
        final ReportDetails reportDetails = this.requestService.readRequestDetails(caseStatus.getReportId());
        List<PaymentOption> payment = null;
        try {
            payment = this.commonService.readPaymentOptions(reportDetails);
        } catch (final Exception e) {
            response
            .addMessage(new WebMessage(
            "Since, this request has a manual configuration for payments. No Automated payment reminder mails will go to user. Please send payment reminders manually if required.",
            MessageSeverity.WARN));
        }
        if (null != payment) {
            boolean mailSent = false;
            for (final PaymentOption paymentOption : payment) {
                if (null != paymentOption.getCaseSubstatus() && paymentOption.getCaseSubstatus().equals(caseStatus.getSubstatus())) {
                    caseStatus.setPaymentOption(paymentOption);
                    caseStatus.setReportDetails(reportDetails);
                    final Notification notification = new Notification(null, LookupConstants.NOTIFICATION_STATUS.Created, reportDetails.getReportId());
                    notification.setByUserId(this.user().getEmailId());
                    notification.setCreationTime(BasicUtils.getCurrentDateTime());
                    notification.setNotificationComment("Payment of Rs. " + paymentOption.getAmountValue() + ""
                    + " to be recieved for report number " + reportDetails.getReportId() + " (Case status changed to "
                    + this.commonService.readLookupMap().get(caseStatus.getSubstatus()).getLookupDescription() + ")");
                    notification.setForUserId(this.user().getEmailId());
                    final GregorianCalendar calendar = new GregorianCalendar();
                    calendar.setTime(BasicUtils.getCurrentDateTime());
                    calendar.add(Calendar.DATE, 1);
                    notification.setNotificationTime(calendar.getTime());
                    this.workflowService.createNotification(notification);
                    response.addMessage(new WebMessage("A notification for tomorrow has been added in your calendar for followup.",
                    MessageSeverity.INFO));
                    this.mailer.sendEmail(caseStatus, reportDetails.getReportId() + ": Case Progress", "CustomerPaymentPending.html",
                    reportDetails.getRaisedFor());
                    response.addMessage(new WebMessage("A mail has been sent to the customer for the payment of Rs. "
                    + paymentOption.getAmountValue(), MessageSeverity.INFO));
                    mailSent = true;
                }
            }
            if (!mailSent) {
                response
                .addMessage(new WebMessage(
                "No Automated payment reminder mails has been sent ot the client for this case status change. If a payment reminder is to be sent. Please send it manually",
                MessageSeverity.WARN));
            }
            this.profileService.createPublicDart(this.user(), caseStatus.getDescription(), caseStatus.getSubstatus(), caseStatus.getReportId());
        }
        return response;
    }

    @RemoteMethod
    public DWRResponse<FileDetails> addFile(final FileDetails fileDetails)
    throws Exception {
        final DWRResponse<FileDetails> response = new DWRResponse<FileDetails>();
        fileDetails.setUploadedBy(this.user().getEmailId());
        fileDetails.setUploadedDt(BasicUtils.getCurrentDateTime());
        response.setMainObject(this.workflowService.addFileDetails(fileDetails));
        response.addMessage(new WebMessage("File Uploaded Successfully", MessageSeverity.INFO));
        this.profileService.createPublicDart(this.user(), "File uploaded", LookupConstants.CASE_SUBSTATUS.FilesHandled, fileDetails.getReportId());
        return response;
    }

    @RemoteMethod
    public DWRResponse<Notification> addNotification(final ReportDetails details, Notification notification)
    throws Exception {
        final DWRResponse<Notification> response = new DWRResponse<Notification>();
        notification.setByUserId(this.user().getEmailId());
        notification.setCreationTime(BasicUtils.getCurrentDateTime());
        notification.setForUserId(details.getAssignedTo());
        notification.setReportId(details.getReportId());
        notification.setNotificationStatus(LookupConstants.NOTIFICATION_STATUS.Created);
        response.setMainObject(notification);
        notification = this.workflowService.createNotification(notification);
        this.profileService.createPublicDart(this.user(), "Added notification: " + notification.getNotificationComment(),
        notification.getNotificationStatus(), notification.getReportId());
        return response;
    }

    @RemoteMethod
    public DWRResponse<Payment> addPayment(final Payment payment)
    throws Exception {
        final DWRResponse<Payment> response = new DWRResponse<Payment>();
        payment.setInvoiceSentInd(0L);
        response.setMainObject(this.workflowService.createPayment(payment));
        response
        .addMessage(new WebMessage(
        "Payment details successfully updated into the system. A mail with the payment receipt has to be sent to the client. Please confirm and send the receipt.",
        MessageSeverity.INFO));
        this.profileService.createPublicDart(this.user(), "Added payment updates: " + payment.getAmount() + " : " + payment.getTransactionRemarks(),
        LookupConstants.CASE_SUBSTATUS.Collection, payment.getReportId());
        return response;
    }

    @RemoteMethod
    public DWRResponse<Conversation> addUpdateConversation(final Conversation conversation)
    throws Exception {
        final DWRResponse<Conversation> response = new DWRResponse<Conversation>();
        conversation.setTimestamp(BasicUtils.getCurrentDateTime());
        conversation.setSentBy(this.user().getEmailId());
        final ReportDetails reportDetails = this.requestService.readRequestDetails(conversation.getReportId());
        if (LookupConstants.USER_ROLE.Customer.equals(this.user().getUserRole())) {
            conversation.setReceivedBy(reportDetails.getAssignedTo());
        } else {
            conversation.setReceivedBy(reportDetails.getRaisedFor());
        }
        final List<FileDetails> uploadedFile = this.getFileList(new FileDetails(null, conversation.getReportId()));
        final List<FileBean> details = new ArrayList<FileBean>();
        for (final FileDetails file : uploadedFile) {
            if (conversation.getAttatchments() != null) {
                for (final Long fileId : conversation.getAttatchments()) {
                    if (fileId == file.getFileId().longValue()) {
                        details.add(file.getFile());
                        break;
                    }
                }
            }
        }
        if (new Long(0).equals(conversation.getDraftInd())) {
            this.mailer.sendEmail(conversation.getMailSubject(), "<div style=\"font-size:12px;font-family:Verdana, Geneva, sans-serif;\">"
            + (conversation.getRemarks() == null ? "" : conversation.getRemarks()) + "</div>", conversation.getReceivedBy(), details, true);
            response.addMessage(new WebMessage("Mail sent successfully.", MessageSeverity.INFO));
            if (!LookupConstants.USER_ROLE.Customer.equals(this.user().getUserRole())) {
                this.profileService.createPublicDart(this.user(), "Sent Communication to Customer", LookupConstants.CASE_SUBSTATUS.Miscellaneous, conversation.getReportId());
            }            
        }
        // inserting conversation into the database
        if (conversation.getConversationId() == null) {
            this.workflowService.createConversation(conversation);
        } else {
            this.workflowService.updateConversation(conversation);
        }
        if (LookupConstants.USER_ROLE.Customer.equals(this.user().getUserRole())) {
            final Notification notification = new Notification();
            notification.setNotificationComment("Client Mail Recieved. Please go to the conversations section to check the mail.");
            final java.util.Calendar c = new GregorianCalendar();
            c.setTime(BasicUtils.getCurrentDateTime());
            c.add(Calendar.HOUR, 12);
            notification.setNotificationTime(c.getTime());
            this.addNotification(reportDetails, notification);
        }
        response.setMainObject(conversation);
        response.addMessage(new WebMessage("Conversation saved successfully", MessageSeverity.INFO));
        return response;
    }

    @RemoteMethod
    public DWRResponse<ReportDetails> calcPayableAmount(final ReportDetails details)
    throws Exception {
        final DWRResponse<ReportDetails> response = new DWRResponse<ReportDetails>();
        try {
            final List<PaymentOption> paymentList = this.commonService.readPaymentOptions(details);
            double totalAmount = 0L;
            for (final PaymentOption payment : paymentList) {
                totalAmount += payment.getAmountValue();
            }
            details.setPayableAmount(totalAmount);
        } catch (final Exception e) {
            response.addMessage(new WebMessage(e.getMessage(), MessageSeverity.INFO));
        }
        response.setMainObject(details);
        return response;
    }

    @RemoteMethod
    public DWRResponse<Notification> changeNotificationStatus(Notification currentNotification)
    throws Exception {
        final DWRResponse<Notification> response = new DWRResponse<Notification>();
        currentNotification.setCreationTime(BasicUtils.getCurrentDateTime());
        currentNotification.setByUserId(this.user().getEmailId());
        if (LookupConstants.NOTIFICATION_STATUS.Created.equals(currentNotification.getNotificationStatus())) {
            currentNotification.setNotificationStatus(LookupConstants.NOTIFICATION_STATUS.Created);
            currentNotification = this.workflowService.createNotification(currentNotification);
            response.addMessage(new WebMessage("Notification added successfully", MessageSeverity.INFO));
        } else {
            this.workflowService.updateNotification(currentNotification);
            if (LookupConstants.NOTIFICATION_STATUS.Completed.equals(currentNotification.getNotificationStatus())) {
                response.addMessage(new WebMessage("Notification Acknowledged Successfully", MessageSeverity.INFO));
            } else if (LookupConstants.NOTIFICATION_STATUS.Abandoned.equals(currentNotification.getNotificationStatus())) {
                response.addMessage(new WebMessage("Notification Deleted Successfully", MessageSeverity.INFO));
            } else if (LookupConstants.NOTIFICATION_STATUS.Rescheduled.equals(currentNotification.getNotificationStatus())) {
                response.addMessage(new WebMessage("Notification Rescheduled Successfully", MessageSeverity.INFO));
            }
        }
        response.setMainObject(currentNotification);
        return response;
    }

    @RemoteMethod
    @AuthenticationBypass
    public WebMessage checkEmail(final String enteredEmail)
    throws Exception {
        if (enteredEmail == null || enteredEmail.trim().length() == 0) {
            return null;
        }
        if (this.requestService.validateEmail(enteredEmail)) {
            return new WebMessage("email id accepted", MessageSeverity.INFO);
        } else {
            return new WebMessage("an account with this email id already exists.", MessageSeverity.ERROR);
        }
    }

    @RemoteMethod
    @AuthenticationBypass
    public DWRResponse<ReportDetails> createRequest(final ReportDetails reportDetails)
    throws Exception {
        final DWRResponse<ReportDetails> response = new DWRResponse<ReportDetails>();
        response.setMainObject(this.requestService.saveRequest(this.user(), reportDetails));
        try {
            this.mailer.sendEmail(response.getMainObject(), "Thank You for your interest in WorldVisa", "CustomerReportRecieved.html", response
            .getMainObject().getRaisedFor());
            
            if (this.user() != null && !LookupConstants.USER_ROLE.Customer.equals(this.user().getUserRole())) {
                this.profileService.createPublicDart(this.user(), "Request Raised", LookupConstants.CASE_SUBSTATUS.RequestCompleted, response.getMainObject().getReportId());
            }
        } catch (final Exception e) {
            // log the exception and do not show it to client
            BaseView.log.error(e);
        }
        response.addMessage(new WebMessage(
        "Thank You for your time. Your request is logged successfully in our system. We will be contacting you shortly. Your Report number is "
        + response.getMainObject().getReportId() + ". Kindly use this as a reference for all further correspondence.", MessageSeverity.INFO));
        return response;
    }

    @RemoteMethod
    public DWRResponse<Boolean> deleteConversation(final Conversation conversation)
    throws Exception {
        final DWRResponse<Boolean> response = new DWRResponse<Boolean>();
        this.workflowService.deleteConversation(conversation);
        response.setMainObject(true);
        response.addMessage(new WebMessage("Conversation Deleted Successfully", MessageSeverity.INFO));
        return response;
    }

    @RemoteMethod
    public DWRResponse<Boolean> deleteFile(final FileDetails fileDetails)
    throws Exception {
        this.workflowService.deleteFileDetails(fileDetails);
        final DWRResponse<Boolean> response = new DWRResponse<Boolean>();
        response.setMainObject(true);
        response.addMessage(new WebMessage("File Deleted Successfully", MessageSeverity.INFO));
        return response;
    }

    @RemoteMethod
    public DWRResponse<Payment> deletePayment(final Payment payment)
    throws Exception {
        final DWRResponse<Payment> response = new DWRResponse<Payment>();
        this.workflowService.deletePayment(new Payment(payment.getPaymentId(), null));
        response.setMainObject(payment);
        if (new Long(1L).equals(payment.getInvoiceSentInd())) {
            response
            .addMessage(new WebMessage(
            "Payment details deleted successfully. A mail with the payment receipt has already been sent to the client. Please confirm the deletion with the client manually.",
            MessageSeverity.WARN));
        } else {
            response.addMessage(new WebMessage(
            "Payment details deleted successfully. The client does not need to be informed since no receipt had been sent.", MessageSeverity.INFO));
        }
        return response;
    }

    @RemoteMethod
    public FileTransfer downloadReceipt(final String paymentId)
    throws Exception {
        final List<Payment> paymentList = this.workflowService.readPaymentList(new Payment(paymentId, null));
        paymentList.get(0).setDetails(this.requestService.readRequestDetails(paymentList.get(0).getReportId()));
        paymentList.get(0).getDetails()
        .setPaymentList(this.workflowService.readPaymentList(new Payment(paymentId, paymentList.get(0).getReportId())));
        return new FileTransfer("PaymentInvoices.pdf", "application/pdf", new FileInputStream(this.generateReport(ReportType.PDF,
        "PaymentInvoice.jrxml", null, paymentList, this.reportGenerator)));
    }

    @RemoteMethod
    public DWRResponse<ReportDetails> freezePayment(ReportDetails details)
    throws Exception {
        final DWRResponse<ReportDetails> response = new DWRResponse<ReportDetails>();
        details = this.workflowService.freezePayment(details);
        this.mailer.sendEmail(details.getPersonalDetails(), "Account Activation Confirmation", "CustomerLoginActivated.html", details.getRaisedFor());
        final CaseStatus caseStatus = new CaseStatus();
        caseStatus.setSubstatus(10602L);
        caseStatus.setDescription("Retainer Aggreement Signed. Started Work on the case.");
        caseStatus.setAddedDate(BasicUtils.getCurrentDateTime());
        caseStatus.setReportId(details.getReportId());
        caseStatus.setSetBy(this.user().getEmailId());
        this.requestService.createCaseStatus(this.user(), caseStatus);
        final Notification notification = new Notification();
        final java.util.Calendar c = new GregorianCalendar();
        c.setTime(BasicUtils.getCurrentDateTime());
        c.add(Calendar.HOUR, 12);
        notification.setNotificationTime(c.getTime());
        notification
        .setNotificationComment("Retainer Aggreement Signed for the report. With this, the first instalment becomes due. Instalment has to be verified");
        this.addNotification(details, notification);
        response.addMessage(new WebMessage("Payment Details Updated Successfully.", MessageSeverity.INFO));
        response.setMainObject(details);
        this.profileService.createPublicDart(this.user(), "Payment Option frozen.", LookupConstants.CASE_SUBSTATUS.Collection, details.getReportId());
        return response;
    }

    @RemoteMethod
    @AuthenticationBypass
    public List<AttributeMaster> getAttributeList(final Long requestType)
    throws Exception {
        return this.requestService.readReportMasterAttributes(requestType);
    }

    @RemoteMethod
    public List<CaseStatus> getCaseStatusList(final CaseStatus caseStatus)
    throws Exception {
        return this.requestService.readCaseStatus(caseStatus.getReportId());
    }

    @RemoteMethod
    public List<Conversation> getConversationList(final Conversation conversation)
    throws Exception {
        final List<Conversation> convList = this.workflowService.readConversations(conversation.getReportId());
        // adding file details to the list
        final List<FileDetails> fileList = this.getFileList(new FileDetails(null, conversation.getReportId()));
        final Map<Long, FileBean> fileMap = new HashMap<Long, FileBean>();
        for (final FileDetails file : fileList) {
            fileMap.put(file.getFileId(), file.getFile());
        }
        for (final Conversation conv : convList) {
            conv.setAttatchmentFiles(new ArrayList<FileBean>());
            final List<Long> attatchments = conv.getAttatchments();
            for (final Long fileId : attatchments) {
                if (fileMap.containsKey(fileId)) {
                    conv.getAttatchmentFiles().add(fileMap.get(fileId));
                }
            }
        }
        return convList;
    }

    @RemoteMethod
    public List<FileDetails> getFileList(final FileDetails fileDetails)
    throws Exception {
        return this.workflowService.readFileDetailsList(new FileDetails(null, fileDetails.getReportId()));
    }

    @RemoteMethod
    public List<Notification> getNotificationsList(final Notification notification)
    throws Exception {
        return this.workflowService.readNotificationListForReport(notification.getReportId());
    }

    @RemoteMethod
    public List<Payment> getPaymentList(final Payment payment)
    throws Exception {
        return this.workflowService.readPaymentList(new Payment(null, payment.getReportId()));
    }

    @RemoteMethod
    public ReportDetails load(final String requestId)
    throws Exception {
        final ReportDetails reportDetails = this.requestService.readRequestDetails(requestId);
        // authorize if the user has access to the report
        final LoginDetails user = this.user();
        if (user.getUserRole().equals(LookupConstants.USER_ROLE.Customer) && !reportDetails.getRaisedFor().equals(user.getEmailId())) {
            throw new AuthorizationException();
        }
        return reportDetails;
    }

    @RemoteMethod
    public DWRResponse<Payment> sendReceipt(final String paymentId)
    throws Exception {
        final DWRResponse<Payment> response = new DWRResponse<Payment>();
        final List<Payment> paymentList = this.workflowService.readPaymentList(new Payment(paymentId, null));
        paymentList.get(0).setDetails(this.requestService.readRequestDetails(paymentList.get(0).getReportId()));
        paymentList.get(0).getDetails()
        .setPaymentList(this.workflowService.readPaymentList(new Payment(paymentId, paymentList.get(0).getReportId())));
        paymentList.get(0).setInvoiceSentInd(1L);
        final File paymentReport = this.generateReport(ReportType.PDF, "PaymentInvoice.jrxml", null, paymentList, this.reportGenerator);
        final FileBean bean = new FileBean();
        bean.setRelativePath(paymentReport.getAbsolutePath());
        bean.setFileName("PaymentInvoice.pdf");
        this.mailer.sendEmail(paymentList.get(0), "Payment Acknowledgement Notification", "CustomerPaymentAcknowledged.html", paymentList.get(0)
        .getDetails().getRaisedFor(), bean);
        this.workflowService.updateInvoiceSentInd(paymentList.get(0));
        response.setMainObject(paymentList.get(0));
        response.addMessage(new WebMessage("A mail with the payment receipt has been sent to the client.", MessageSeverity.INFO));
        return response;
    }

    @RemoteMethod
    public List<Notification> showNotificationHistory(final Long NotificationId)
    throws Exception {
        return this.workflowService.readNotificationList(new Notification(NotificationId, null, null));
    }

    @RemoteMethod
    public DWRResponse<ReportDetails> updateRequest(final ReportDetails reportDetails)
    throws Exception {
        final DWRResponse<ReportDetails> response = new DWRResponse<ReportDetails>();
        response.setMainObject(this.requestService.updateRequest(reportDetails, reportDetails.getRaisedFor(), this.user()));
        response.addMessage(new WebMessage("Request Details Successfully Updated.", MessageSeverity.INFO));
        return response;
    }
}
