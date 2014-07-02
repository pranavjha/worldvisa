/******************************************************************************
 * Name : Conversation.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for Conversation
 *****************************************************************************/
package com.worldvisa.business.domain.workflow;

import java.util.ArrayList;
import java.util.List;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.domain.common.FileBean;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for Conversation.
 */
@DataTransferObject(converter = BeanConverter.class)
public class Conversation
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private List<FileBean>    attatchmentFiles;
    private List<Long>        attatchments;
    private Long              conversationId;
    private Long              draftInd;
    private String            mailSubject;
    private String            receivedBy;
    private String            remarks;
    private String            reportId;
    private String            sentBy;
    private java.util.Date    timestamp;

    /**
     * Instantiates Conversation with defaults
     */
    public Conversation() {
        super();
        this.attatchments = new ArrayList<Long>();
    }

    /**
     * Instantiates Conversation with primary key combination
     */
    public Conversation(final Long conversationId, final String reportId) {
        super();
        this.conversationId = conversationId;
        this.reportId = reportId;
        this.attatchments = new ArrayList<Long>();
    }

    /**
     * @return the attatchmentFiles
     */
    public List<FileBean> getAttatchmentFiles() {
        return this.attatchmentFiles;
    }

    /**
     * @return the attatchments
     */
    public List<Long> getAttatchments() {
        return this.attatchments;
    }

    /**
     * Getter method for conversationId
     * @return conversationId
     */
    public Long getConversationId() {
        return this.conversationId;
    }

    /**
     * @return the draftInd
     */
    public Long getDraftInd() {
        return this.draftInd;
    }

    /**
     * @return the fileDetails
     */
    public String getFileDetails() {
        String files = "";
        if (this.attatchments == null || this.attatchments.size() == 0) {
            return null;
        } else {
            for (final Long fileId : this.attatchments) {
                files = files + "," + fileId;
            }
            return files.substring(1);
        }
    }

    /**
     * @return the mailSubject
     */
    public String getMailSubject() {
        return this.mailSubject;
    }

    /**
     * Getter method for receivedBy
     * @return receivedBy
     */
    public String getReceivedBy() {
        return this.receivedBy;
    }

    /**
     * Getter method for remarks
     * @return remarks
     */
    public String getRemarks() {
        return this.remarks;
    }

    /**
     * Getter method for reportId
     * @return reportId
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * Getter method for sentBy
     * @return sentBy
     */
    public String getSentBy() {
        return this.sentBy;
    }

    /**
     * Getter method for timestamp
     * @return timestamp
     */
    public java.util.Date getTimestamp() {
        return this.timestamp;
    }

    /**
     * @param attatchmentFiles the attatchmentFiles to set
     */
    public void setAttatchmentFiles(final List<FileBean> attatchmentFiles) {
        this.attatchmentFiles = attatchmentFiles;
    }

    /**
     * @param attatchments the attatchments to set
     */
    public void setAttatchments(final List<Long> attatchments) {
        this.attatchments = attatchments;
    }

    /**
     * Setter method for conversationId
     * @param conversationId Value for conversationId
     */
    public void setConversationId(final Long conversationId) {
        this.conversationId = conversationId;
    }

    /**
     * @param draftInd the draftInd to set
     */
    public void setDraftInd(final Long draftInd) {
        this.draftInd = draftInd;
    }

    /**
     * @param fileDetails the fileDetails to set
     */
    public void setFileDetails(final String fileDetails) {
        this.attatchments = new ArrayList<Long>();
        if (fileDetails == null || fileDetails.trim().length() == 0) {
            return;
        } else {
            final String[] fileIds = fileDetails.split("[,]");
            for (final String file : fileIds) {
                this.attatchments.add(new Long(file));
            }
        }
    }

    /**
     * @param mailSubject the mailSubject to set
     */
    public void setMailSubject(final String mailSubject) {
        this.mailSubject = mailSubject;
    }

    /**
     * Setter method for receivedBy
     * @param receivedBy Value for receivedBy
     */
    public void setReceivedBy(final String receivedBy) {
        this.receivedBy = receivedBy;
    }

    /**
     * Setter method for remarks
     * @param remarks Value for remarks
     */
    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }

    /**
     * Setter method for reportId
     * @param reportId Value for reportId
     */
    public void setReportId(final String reportId) {
        this.reportId = reportId;
    }

    /**
     * Setter method for sentBy
     * @param sentBy Value for sentBy
     */
    public void setSentBy(final String sentBy) {
        this.sentBy = sentBy;
    }

    /**
     * Setter method for timestamp
     * @param timestamp Value for timestamp
     */
    public void setTimestamp(final java.util.Date timestamp) {
        this.timestamp = timestamp;
    }
}
