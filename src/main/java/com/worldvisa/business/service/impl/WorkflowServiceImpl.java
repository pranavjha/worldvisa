/******************************************************************************
 * Name : WorkflowServiceImpl.java Author : Administrator Date : 09-May-2010,2:28:18 AM Description : Implementation for WorkflowServiceImpl
 *****************************************************************************/
package com.worldvisa.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.worldvisa.business.dao.profile.LoginDetailsDAO;
import com.worldvisa.business.dao.profile.UserDetailsDAO;
import com.worldvisa.business.dao.request.ReportDetailsDAO;
import com.worldvisa.business.dao.workflow.ConversationDAO;
import com.worldvisa.business.dao.workflow.FileDetailsDAO;
import com.worldvisa.business.dao.workflow.NotificationDAO;
import com.worldvisa.business.dao.workflow.PaymentDAO;
import com.worldvisa.business.domain.common.ContextAwareDataTableRequest;
import com.worldvisa.business.domain.profile.LoginDetails;
import com.worldvisa.business.domain.profile.UserDetails;
import com.worldvisa.business.domain.request.ReportDetails;
import com.worldvisa.business.domain.workflow.Conversation;
import com.worldvisa.business.domain.workflow.FileDetails;
import com.worldvisa.business.domain.workflow.Notification;
import com.worldvisa.business.domain.workflow.Payment;
import com.worldvisa.business.domain.workflow.TransactionRecordsFilter;
import com.worldvisa.business.infra.base.BaseServiceImpl;
import com.worldvisa.business.infra.constants.LookupConstants;
import com.worldvisa.business.service.stub.FileHandlerService;
import com.worldvisa.business.service.stub.WorkflowService;

/**
 * 
 */
@Service
public class WorkflowServiceImpl
extends BaseServiceImpl
implements WorkflowService {
    @Autowired
    private ConversationDAO    conversationDAO;
    @Autowired
    private FileDetailsDAO     fileDetailsDAO;
    @Autowired
    private FileHandlerService fileHandlerService;
    @Autowired
    private LoginDetailsDAO    loginDetailsDAO;
    @Autowired
    private NotificationDAO    notificationDAO;
    @Autowired
    private PaymentDAO         paymentDAO;
    @Autowired
    private ReportDetailsDAO   reportDetailsDAO;
    @Autowired
    private UserDetailsDAO     userDetailsDAO;

    /**
     * @see com.worldvisa.business.service.stub.WorkflowService#addFileDetails(com.worldvisa.business.domain.workflow.FileDetails)
     */
    public FileDetails addFileDetails(final FileDetails fileDetails)
    throws Exception {
        fileDetails.setFileId((Long) this.fileDetailsDAO.create(fileDetails));
        final String newRelativePath = "WORKFLOW_FILE_DETAILS/" + fileDetails.getReportId() + "/" + fileDetails.getFileId() + "."
        + fileDetails.getFile().getFileName();
        this.fileHandlerService.updateFile(fileDetails.getFile().getRelativePath(), newRelativePath);
        fileDetails.getFile().setRelativePath(newRelativePath);
        return fileDetails;
    }

    /**
     * @see com.worldvisa.business.service.stub.RequestService#createConversation(com.worldvisa.business.domain.workflow.Conversation)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Conversation createConversation(final Conversation newConversation)
    throws Exception {
        try {
            final Long conversationId = (Long) this.conversationDAO.create(newConversation);
            newConversation.setConversationId(conversationId);
            return newConversation;
        } catch (final Exception e) {
            final Exception ex = new Exception(
            "The system could not save the conversation details. Try adding the conversation again. If the problem persists, contact administrator for assistance.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
    }

    /**
     * @see com.worldvisa.business.service.stub.RequestService#createNotification(com.worldvisa.business.domain.workflow.Notification)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Notification createNotification(final Notification notification)
    throws Exception {
        try {
            notification.setNotificationId((Long) this.notificationDAO.create(notification));
        } catch (final Exception e) {
            final Exception ex = new Exception(
            "The system could not add notification. Try adding the notification again. If the problem persists, contact administrator for assistance.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        return notification;
    }

    /**
     * @see com.worldvisa.business.service.stub.WorkflowService#createPayment(com.worldvisa.business.domain.workflow.Payment)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Payment createPayment(final Payment newPayment)
    throws Exception {
        try {
            newPayment.setPaymentId((String) this.paymentDAO.create(newPayment));
            return newPayment;
        } catch (final Exception e) {
            final Exception ex = new Exception(
            "The system could not add the payment details. Please try again. If the problem persists, contact administrator for assistance.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
    }

    /**
     * @see com.worldvisa.business.service.stub.RequestService#deleteConversation(com.worldvisa.business.domain.workflow.Conversation)
     */
    public Conversation deleteConversation(final Conversation newConversation)
    throws Exception {
        final Conversation filter = new Conversation(newConversation.getConversationId(), newConversation.getReportId());
        this.conversationDAO.delete(filter);
        return newConversation;
    }

    /**
     * @see com.worldvisa.business.service.stub.WorkflowService#deleteFileDetails(com.worldvisa.business.domain.workflow.FileDetails)
     */
    public void deleteFileDetails(final FileDetails fileDetails)
    throws Exception {
        this.fileHandlerService.deleteRecursive(fileDetails.getFile().getRelativePath());
        this.fileDetailsDAO.delete(new FileDetails(fileDetails.getFileId(), fileDetails.getReportId()));
    }

    /**
     * @see com.worldvisa.business.service.stub.RequestService#deletePayment(com.worldvisa.business.domain.workflow.Payment)
     */
    public Payment deletePayment(final Payment payment)
    throws Exception {
        this.paymentDAO.delete(payment);
        return payment;
    }

    /**
     * @see com.worldvisa.business.service.stub.RequestService#freezePayment(com.worldvisa.business.domain.request.ReportDetails)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public ReportDetails freezePayment(final ReportDetails reportDetails)
    throws Exception {
        try {
            this.reportDetailsDAO.update(reportDetails, new ReportDetails(reportDetails.getReportId()));
            reportDetails.setPersonalDetails(this.userDetailsDAO.read(new UserDetails(null, reportDetails.getRaisedFor())));
            reportDetails.getPersonalDetails().setRegistrationStatus(LookupConstants.CLIENT_REG_STATUS.Registerd);
            reportDetails.getPersonalDetails().setIsActive(1L);
            this.loginDetailsDAO.update(new LoginDetails(reportDetails.getPersonalDetails()), new LoginDetails(reportDetails.getRaisedFor()));
        } catch (final Exception e) {
            final Exception ex = new Exception(
            "The system could not freeze the payment details. Please try again. If the problem persists, contact administrator for assistance.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        return reportDetails;
    }

    public List<Conversation> readConversations(final String reportId)
    throws Exception {
        return this.conversationDAO.readList(new Conversation(null, reportId));
    }

    /**
     * @see com.worldvisa.business.service.stub.WorkflowService#readFileDetailsList(com.worldvisa.business.domain.workflow.FileDetails)
     */
    public List<FileDetails> readFileDetailsList(final FileDetails fileDetails)
    throws Exception {
        return this.fileDetailsDAO.readList(fileDetails);
    }

    public List<Notification> readNotification(final ContextAwareDataTableRequest dataTableRequest)
    throws Exception {
        return this.notificationDAO.readListForUser(dataTableRequest);
    }

    /**
     * @see com.worldvisa.business.service.stub.WorkflowService#readNotificationList(com.worldvisa.business.domain.workflow.Notification)
     */
    public List<Notification> readNotificationList(final Notification notification)
    throws Exception {
        return this.notificationDAO.readList(notification);
    }

    public int readNotificationListCount(final ContextAwareDataTableRequest dataTableRequest)
    throws Exception {
        return this.notificationDAO.readLengthForUser(dataTableRequest);
    }

    /**
     * @see com.worldvisa.business.service.stub.WorkflowService#readNotificationList(com.worldvisa.business.domain.workflow.Notification)
     */
    public List<Notification> readNotificationListForReport(final String reportId)
    throws Exception {
        return this.notificationDAO.readListForReport(reportId);
    }

    /**
     * @see com.worldvisa.business.service.stub.RequestService#readPaymentList(com.worldvisa.business.domain.workflow.Payment)
     */
    public List<Payment> readPaymentList(final Payment payment)
    throws Exception {
        return this.paymentDAO.readList(payment);
    }

    /**
     * @see com.worldvisa.business.service.stub.WorkflowService#readPaymentRecords(com.worldvisa.business.domain.workflow.TransactionRecordsFilter)
     */
    public List<Payment> readPaymentRecords(final TransactionRecordsFilter filter)
    throws Exception {
        return this.paymentDAO.readSearchList(filter);
    }

    /**
     * @see com.worldvisa.business.service.stub.WorkflowService#readPaymentRecordsCount(com.worldvisa.business.domain.workflow.TransactionRecordsFilter)
     */
    public int readPaymentRecordsCount(final TransactionRecordsFilter filter)
    throws Exception {
        return this.paymentDAO.readSearchListCount(filter);
    }

    /**
     * @see com.worldvisa.business.service.stub.RequestService#updateConversation(com.worldvisa.business.domain.workflow.Conversation)
     */
    public Conversation updateConversation(final Conversation newConversation)
    throws Exception {
        final Conversation filter = new Conversation(newConversation.getConversationId(), newConversation.getReportId());
        this.conversationDAO.update(newConversation, filter);
        return newConversation;
    }

    /**
     * @see com.worldvisa.business.service.stub.RequestService#updateInvoiceSentInd(com.worldvisa.business.domain.workflow.Payment)
     */
    public Payment updateInvoiceSentInd(final Payment payment)
    throws Exception {
        this.paymentDAO.update(payment, new Payment(payment.getPaymentId(), null));
        return payment;
    }

    /**
     * @see com.worldvisa.business.service.stub.WorkflowService#updateNotification(com.worldvisa.business.domain.workflow.Notification)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Notification updateNotification(final Notification currentNotification)
    throws Exception {
        this.notificationDAO.update(currentNotification, null);
        return currentNotification;
    }
}
