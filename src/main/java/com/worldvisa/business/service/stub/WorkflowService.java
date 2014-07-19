/******************************************************************************
 * Name : WorkflowServiceImpl.java Author : Administrator Date : 09-May-2010,2:28:18 AM Description : Implementation for WorkflowServiceImpl
 *****************************************************************************/
package com.worldvisa.business.service.stub;

import java.util.List;
import com.worldvisa.business.domain.common.ContextAwareDataTableRequest;
import com.worldvisa.business.domain.request.ReportDetails;
import com.worldvisa.business.domain.workflow.Conversation;
import com.worldvisa.business.domain.workflow.FileDetails;
import com.worldvisa.business.domain.workflow.Notification;
import com.worldvisa.business.domain.workflow.Payment;
import com.worldvisa.business.domain.workflow.TransactionRecordsFilter;

/**
 * 
 */
public interface WorkflowService {
    public FileDetails addFileDetails(FileDetails fileDetails)
    throws Exception;

    public Conversation createConversation(Conversation newConversation)
    throws Exception;

    public Notification createNotification(Notification newNotification)
    throws Exception;

    public Payment createPayment(Payment newPayment)
    throws Exception;

    public Conversation deleteConversation(Conversation conversation)
    throws Exception;

    public void deleteFileDetails(FileDetails fileDetails)
    throws Exception;

    public Payment deletePayment(Payment payment)
    throws Exception;

    public ReportDetails freezePayment(ReportDetails reportDetails)
    throws Exception;

    public List<Conversation> readConversations(String reportId)
    throws Exception;

    public List<FileDetails> readFileDetailsList(FileDetails fileDetails)
    throws Exception;

    public List<Notification> readNotification(ContextAwareDataTableRequest dataTableRequest)
    throws Exception;

    public List<Notification> readNotificationList(Notification notification)
    throws Exception;

    public int readNotificationListCount(ContextAwareDataTableRequest contextAwareDataTableRequest)
    throws Exception;

    public List<Notification> readNotificationListForReport(String reportId)
    throws Exception;

    public List<Payment> readPaymentList(Payment payment)
    throws Exception;

    public List<Payment> readPaymentRecords(TransactionRecordsFilter transactionRecordsFilter)
    throws Exception;

    public int readPaymentRecordsCount(TransactionRecordsFilter transactionRecordsFilter)
    throws Exception;

    public Conversation updateConversation(Conversation conversation)
    throws Exception;

    public Payment updateInvoiceSentInd(Payment payment)
    throws Exception;

    public Notification updateNotification(Notification notification)
    throws Exception;
}
